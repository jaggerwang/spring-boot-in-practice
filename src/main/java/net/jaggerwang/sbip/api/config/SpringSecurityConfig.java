package net.jaggerwang.sbip.api.config;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import net.jaggerwang.sbip.adapter.controller.dto.RootDto;
import net.jaggerwang.sbip.adapter.controller.dto.UserDto;
import net.jaggerwang.sbip.api.security.JsonUsernamePasswordAuthenticationFilter;
import net.jaggerwang.sbip.api.security.LoggedUser;
import net.jaggerwang.sbip.usecase.UserUsecases;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserUsecases userUsecases;

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
        http.csrf().disable()

                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
                    responseJson(response, HttpStatus.UNAUTHORIZED, new RootDto("unauthenticated", "未认证"));
                }).accessDeniedHandler((request, response, accessDeniedException) -> {
                    responseJson(response, HttpStatus.FORBIDDEN, new RootDto("unauthorized", "未授权"));
                })

                .and().logout().logoutSuccessHandler((request, response, authentication) -> {
                    responseJson(response, HttpStatus.OK, new RootDto());
                })

                .and().addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)

                .authorizeRequests()
                .antMatchers("/graphql", "/subscriptions", "/graphiql", "/voyager", "/vendor/**", "/actuator/**",
                        "/files/**", "/user/register", "/user/login", "/user/logged")
                .permitAll().anyRequest().authenticated();
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter authFilter() throws Exception {
        var authFilter = new JsonUsernamePasswordAuthenticationFilter();
        authFilter.setAuthenticationManager(authenticationManagerBean());
        authFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            var loggedUser = (LoggedUser) authentication.getPrincipal();
            var userEntity = userUsecases.info(loggedUser.getId());

            responseJson(response, HttpStatus.OK, new RootDto().addDataEntry("user", UserDto.fromEntity(userEntity)));
        });
        authFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
                responseJson(response, HttpStatus.OK, new RootDto("fail", "用户名或密码错误"));
            } else {
                responseJson(response, HttpStatus.INTERNAL_SERVER_ERROR, new RootDto("fail", exception.toString()));
            }
        });
        return authFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
