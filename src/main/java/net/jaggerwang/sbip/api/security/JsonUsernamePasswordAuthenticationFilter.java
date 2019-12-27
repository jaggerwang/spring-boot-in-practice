package net.jaggerwang.sbip.api.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.jaggerwang.sbip.adapter.controller.dto.UserDto;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username, password;
        try {
            var userDto = objectMapper.readValue(request.getInputStream(), UserDto.class);
            if (userDto.getUsername() != null) {
                username = userDto.getUsername();
            } else if (userDto.getMobile() != null) {
                username = userDto.getMobile();
            } else if (userDto.getEmail() != null) {
                username = userDto.getEmail();
            } else {
                username = null;
            }
            password = userDto.getPassword();
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim();
        var authRequest = new UsernamePasswordAuthenticationToken(username, password);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
