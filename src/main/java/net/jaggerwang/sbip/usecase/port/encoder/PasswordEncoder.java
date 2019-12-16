package net.jaggerwang.sbip.usecase.port.encoder;

public interface PasswordEncoder {
    String encode(String password);

    Boolean matches(String rawPassword, String encodedPassword);
}
