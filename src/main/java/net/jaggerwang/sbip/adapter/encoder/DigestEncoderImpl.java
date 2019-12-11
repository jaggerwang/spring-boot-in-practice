package net.jaggerwang.sbip.adapter.encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.usecase.port.encoder.DigestEncoder;

@Component
public class DigestEncoderImpl implements DigestEncoder {
    @Override
    public String sha512(String content, String salt) {
        try {
            var md = MessageDigest.getInstance("SHA-512");
            return md.digest((content + salt).getBytes()).toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
