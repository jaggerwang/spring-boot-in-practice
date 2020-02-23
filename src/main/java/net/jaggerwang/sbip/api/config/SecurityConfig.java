package net.jaggerwang.sbip.api.config;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import net.jaggerwang.sbip.adapter.controller.dto.RootDto;
import net.jaggerwang.sbip.adapter.controller.dto.UserDto;
import net.jaggerwang.sbip.api.security.JsonUsernamePasswordAuthenticationFilter;
import net.jaggerwang.sbip.api.security.LoggedUser;
import net.jaggerwang.sbip.usecase.UserUsecase;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private ObjectMapper objectMapper;
    private UserUsecase userUsecase;

    public SecurityConfig(ObjectMapper objectMapper, UserUsecase userUsecase) {
        this.objectMapper = objectMapper;
        this.userUsecase = userUsecase;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void responseJson(HttpServletResponse response, HttpStatus status, RootDto data) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(data));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .addFilter(authFilter())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                responseJson(response, HttpStatus.UNAUTHORIZED,
                                        new RootDto("unauthenticated", "未认证")))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                responseJson(response, HttpStatus.FORBIDDEN,
                                        new RootDto("unauthorized", "未授权")))
                )
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) ->
                                responseJson(response, HttpStatus.OK, new RootDto()))
                )
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/", "/favicon.ico", "/csrf", "/vendor/**", "/webjars/**",
                                "/actuator/**", "/graphql", "/subscriptions", "/graphiql",
                                "/voyager", "/v2/api-docs", "/swagger-ui.html", "/swagger-resources/**",
                                "/files/**", "/user/register", "/user/login", "/user/logged")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );
    }

    // Avoid to using `authenticationManager` as method name, as it is already defined in super class.
    @Bean
    public AuthenticationManager authManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter authFilter() throws Exception {
        var authFilter = new JsonUsernamePasswordAuthenticationFilter();
        authFilter.setAuthenticationManager(authManager());
        authFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            var loggedUser = (LoggedUser) authentication.getPrincipal();
            var userEntity = userUsecase.info(loggedUser.getId());

            responseJson(response, HttpStatus.OK,
                    new RootDto().addDataEntry("user", userEntity.map(UserDto::fromEntity).get()));
        });
        authFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            if (exception instanceof UsernameNotFoundException
                    || exception instanceof BadCredentialsException) {
                responseJson(response, HttpStatus.OK, new RootDto("fail", "用户名或密码错误"));
            } else {
                responseJson(response, HttpStatus.INTERNAL_SERVER_ERROR,
                        new RootDto("fail", exception.toString()));
            }
        });
        return authFilter;
    }
}
