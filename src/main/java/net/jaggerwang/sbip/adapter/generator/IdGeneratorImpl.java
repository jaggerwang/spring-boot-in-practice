package net.jaggerwang.sbip.adapter.generator;

import java.util.UUID;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.usecase.port.generator.IdGenerator;

@Component
public class IdGeneratorImpl implements IdGenerator {
    @Override
    public String objectId() {
        return new ObjectId().toHexString();
    }

    @Override
    public String uuid() {
        return UUID.randomUUID().toString();
    }
}
