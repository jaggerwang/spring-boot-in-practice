package net.jaggerwang.sbip.util.generator;

import java.util.UUID;
import org.bson.types.ObjectId;

/**
 * @author Jagger Wang
 */
public class IdGenerator {
    public String objectId() {
        return new ObjectId().toHexString();
    }

    public String uuid() {
        return UUID.randomUUID().toString();
    }
}
