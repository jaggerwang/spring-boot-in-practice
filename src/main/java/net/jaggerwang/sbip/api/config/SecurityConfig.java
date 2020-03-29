package net.jaggerwang.sbip.api.config;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import net.jaggerwang.sbip.adapter.controller.dto.RootDto;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private ObjectMapper objectMapper;

    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private void responseJson(HttpServletResponse response, HttpStatus status, RootDto data) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        response.getWriter().write(objectMapper.writeValueAsString(data));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // TODO: It will cause '/login' page not found.
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .authenticationEntryPoint((request, response, authException) ->
//                                responseJson(response, HttpStatus.UNAUTHORIZED,
//                                        new RootDto("unauthenticated", "未认证")))
//                        .accessDeniedHandler((request, response, accessDeniedException) ->
//                                responseJson(response, HttpStatus.FORBIDDEN,
//                                        new RootDto("unauthorized", "未授权")))
//                )
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/favicon.ico", "/csrf", "/vendor/**", "/webjars/**",
                                "/actuator/**", "/v2/api-docs", "/swagger-ui.html",
                                "/swagger-resources/**", "/", "/graphql", "/login", "/logout",
                                "/auth/login", "/auth/logout", "/auth/logged", "/user/register",
                                "/files/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(formLogin -> {})
                .logout(logout -> {});
    }
}
