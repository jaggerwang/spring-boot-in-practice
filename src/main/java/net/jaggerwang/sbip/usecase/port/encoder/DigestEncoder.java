package net.jaggerwang.sbip.usecase.port.encoder;

public interface DigestEncoder {
    default String sha512(String content) {
        return sha512(content, "");
    }

    String sha512(String content, String salt);
}
