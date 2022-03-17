package com.pigumer.tools.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    void testOpenAPIGeneratorVersion() {
        Main main = new Main();
        String[] args = { "OpenAPIGeneratorVersion", "-f", "build.yml" };
        int status = main.command(args);
        Assertions.assertEquals(0, status);
    }
}
