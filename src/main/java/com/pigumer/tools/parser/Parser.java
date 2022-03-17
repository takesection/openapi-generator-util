/*
 * @author Shigeki Shoji
 */
package com.pigumer.tools.parser;

import com.pigumer.tools.model.Config;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class Parser {

    public Config parse(InputStream in) {
        Yaml yaml = new Yaml();
        return yaml.loadAs(in, Config.class);
    }
}
