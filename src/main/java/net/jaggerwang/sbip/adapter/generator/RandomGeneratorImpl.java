package net.jaggerwang.sbip.adapter.generator;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.usecase.port.generator.RandomGenerator;

@Component
public class RandomGeneratorImpl implements RandomGenerator {
    static final String upperLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String lowerLetters = "abcdefghijklmnopqrstuvwxyz";
    static final String numbers = "0123456789";
    static final String upperHexChars = "0123456789ABCDEF";
    static final String lowerHexChars = "0123456789abcdef";

    static final SecureRandom random = new SecureRandom();

    @Override
    public String randomString(Integer len, String chars) {
        var sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Override
    public String letterString(Integer len, Boolean upper) {
        var chars = upperLetters + lowerLetters;
        if (upper != null) {
            chars = upper ? upperLetters : lowerLetters;
        }
        return randomString(len, chars);
    }

    @Override
    public String letterNumberString(Integer len, Boolean upper) {
        var chars = upperLetters + lowerLetters;
        if (upper != null) {
            chars = upper ? upperLetters : lowerLetters;
        }
        chars += numbers;
        return randomString(len, chars);
    }

    @Override
    public String numberString(Integer len) {
        return randomString(len, numbers);
    }

    @Override
    public String hexString(Integer len, Boolean upper) {
        var chars = upper ? upperHexChars : lowerHexChars;
        return randomString(len, chars);
    }
}
