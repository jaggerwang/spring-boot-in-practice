package net.jaggerwang.sbip.usecase.port.generator;

public interface RandomGenerator {
    String randomString(int len, String chars);

    String letterString(int len, Boolean upper);

    String letterNumberString(int len, Boolean upper);

    String numberString(int len);

    String hexString(int len, Boolean upper);
}
