package net.jaggerwang.sbip.usecase.port.generator;

import java.util.Optional;

public interface RandomGenerator {
    String randomString(int len, String chars);

    String letterString(int len, Optional<Boolean> upper);

    String letterNumberString(int len, Optional<Boolean> upper);

    String numberString(int len);

    String hexString(int len, Boolean upper);
}
