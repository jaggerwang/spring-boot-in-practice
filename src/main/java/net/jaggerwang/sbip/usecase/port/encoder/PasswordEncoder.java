package net.jaggerwang.sbip.usecase.port.encoder;

public interface PasswordEncoder {
    String encode(String password);

    boolean matches(String rawPassword, String encodedPassword);
}
