package com.pigumer.tools.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigTest {

    private Config config = new Config();

    @Test
    public void packageNameTest() {
        config.validatePackageName("com.example_example");
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName(""));
        System.out.println(ex.getMessage());
        ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName("."));
        System.out.println(ex.getMessage());
        ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName("com."));
        System.out.println(ex.getMessage());
        ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName("com..example"));
        System.out.println(ex.getMessage());
        ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName("com.123"));
        System.out.println(ex.getMessage());
        ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName("com.ex-ample"));
        System.out.println(ex.getMessage());
        ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName("com.goto"));
        System.out.println(ex.getMessage());
        ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName("com.true"));
        System.out.println(ex.getMessage());
        ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName("com.false"));
        System.out.println(ex.getMessage());
        ex = Assertions.assertThrows(RuntimeException.class, () -> config.validatePackageName("com.null"));
        System.out.println(ex.getMessage());
    }
}
