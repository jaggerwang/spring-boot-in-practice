package net.jaggerwang.sbip.util.generator;

import java.security.SecureRandom;
import java.util.Optional;

/**
 * @author Jagger Wang
 */
public class RandomGenerator {
    static final String UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    static final String NUMBERS = "0123456789";
    static final String UPPER_HEX_CHARS = "0123456789ABCDEF";
    static final String LOWER_HEX_CHARS = "0123456789abcdef";

    static final SecureRandom RANDOM = new SecureRandom();

    public String randomString(int len, String chars) {
        var sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(RANDOM.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public String letterString(int len, Boolean upper) {
        var chars = UPPER_LETTERS + LOWER_LETTERS;
        if (upper != null) {
            chars = upper ? UPPER_LETTERS : LOWER_LETTERS;
        }
        return randomString(len, chars);
    }

    public String letterNumberString(int len, Boolean upper) {
        var chars = UPPER_LETTERS + LOWER_LETTERS;
        if (upper != null) {
            chars = upper ? UPPER_LETTERS : LOWER_LETTERS;
        }
        chars += NUMBERS;
        return randomString(len, chars);
    }

    public String numberString(int len) {
        return randomString(len, NUMBERS);
    }

    public String hexString(int len, Boolean upper) {
        var chars = upper ? UPPER_HEX_CHARS : LOWER_HEX_CHARS;
        return randomString(len, chars);
    }
}
