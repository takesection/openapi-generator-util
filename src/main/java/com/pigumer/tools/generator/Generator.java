/*
 * https://github.com/samskivert/jmustache
 *
 * @author Shigeki Shoji
 */
package com.pigumer.tools.generator;

import com.pigumer.tools.download.Download;
import com.pigumer.tools.model.Module;
import com.pigumer.tools.model.Config;
import com.pigumer.tools.parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Generator {

    public void generateAll(String file) throws IOException, InterruptedException {
        try (FileInputStream in = new FileInputStream(Paths.get(file).toFile())) {
            Parser parser = new Parser();
            Config settings = parser.parse(in);
            for (Module module: settings.getModules()) {
                String name = module.getName();
                String options = module.getOptions();
                generate(name, options);
            }
        }
    }

    public void generate(String name, String options) throws InterruptedException, IOException {
        List<String> command = new ArrayList<>();
        command.add("java");
        if (options != null) {
            command.addAll(Arrays.asList(options.split(" ")));
        }
        command.add("-jar");
        command.add(Download.name);
        command.add("batch");
        command.add(name);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Map<String, String> env = processBuilder.environment();
        String path = new File(".").getCanonicalPath();
        processBuilder.directory(new File(path));
        System.out.println(processBuilder.directory().getAbsolutePath());
        processBuilder.redirectErrorStream(true);
        File log = new File("log");
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        Process process = processBuilder.start();
        int exit = process.waitFor();
        if (exit != 0) {
            throw new InterruptedException("Exit Code = " + exit);
        }
    }
}
