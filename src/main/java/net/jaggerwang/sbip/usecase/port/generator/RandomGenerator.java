package net.jaggerwang.sbip.usecase.port.generator;

public interface RandomGenerator {
    String randomString(Integer len, String chars);

    String letterString(Integer len, Boolean upper);

    String letterNumberString(Integer len, Boolean upper);

    String numberString(Integer len);

    String hexString(Integer len, Boolean upper);
}
