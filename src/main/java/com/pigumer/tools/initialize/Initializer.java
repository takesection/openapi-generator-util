/*
 * @author Shigeki Shoji
 */
package com.pigumer.tools.initialize;

import com.pigumer.tools.model.Module;
import com.pigumer.tools.model.Config;
import com.pigumer.tools.parser.Parser;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Initializer {

    public void initAll(String file) throws IOException {
        try (FileInputStream in = new FileInputStream(Paths.get(file).toFile())) {
            Parser parser = new Parser();
            Config settings = parser.parse(in);
            Map<String, String> properties = settings.getProperties();
            for (Module module: settings.getModules()) {
                Path path = Paths.get(module.getName());
                String body = generate(module.getTemplate(), properties);
                Files.writeString(path, body, StandardCharsets.UTF_8);
            }
        }
    }

    public String generate(Map<String, Object> rawTemplate, Map<String, String> properties) {
        Yaml yaml = new Yaml();
        Template template = Mustache.compiler().compile(yaml.dump(rawTemplate));
        return template.execute(properties);
    }
}
