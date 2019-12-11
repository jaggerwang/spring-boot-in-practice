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

import net.jaggerwang.sbip.adapter.controller.dto.UserDTO;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username, password;
        try {
            UserDTO userDTO = objectMapper.readValue(request.getInputStream(), UserDTO.class);
            if (userDTO.getUsername() != null) {
                username = userDTO.getUsername();
            } else if (userDTO.getMobile() != null) {
                username = userDTO.getMobile();
            } else if (userDTO.getEmail() != null) {
                username = userDTO.getEmail();
            } else {
                username = null;
            }
            password = userDTO.getPassword();
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
