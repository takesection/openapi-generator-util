package com.pigumer.tools.parser;

import com.pigumer.tools.initialize.Initializer;
import com.pigumer.tools.model.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

/**
 * https://code.google.com/archive/p/snakeyaml/wikis/Documentation.wiki
 */
public class ParserTest {

    @Test
    void parseTest() throws IOException {
        Parser parser = new Parser();
        try (FileInputStream in = new FileInputStream(Paths.get("build.yml").toFile())) {
            Config settings = parser.parse(in);

            Initializer initializer = new Initializer();
            Map<String, String> properties = settings.getProperties();
            String template = initializer.generate(settings.getModules().get(0).getTemplate(), properties);
            Assertions.assertNotNull(template);
            System.out.println(template);
        }
    }
}
