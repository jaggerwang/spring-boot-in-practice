package net.jaggerwang.sbip.adapter.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.usecase.port.encoder.PasswordEncoder;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }
}
