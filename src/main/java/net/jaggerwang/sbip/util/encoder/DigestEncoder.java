package net.jaggerwang.sbip.util.encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Jagger Wang
 */
public class DigestEncoder {
    public String sha512(String content, String salt) {
        try {
            var md = MessageDigest.getInstance("SHA-512");
            return Arrays.toString(md.digest((content + salt).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
