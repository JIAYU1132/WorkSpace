package test;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TestUUID {

    @Test
    public void t1() {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
    }
}
