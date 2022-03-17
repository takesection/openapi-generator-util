/*
 * @author Shigeki Shoji
 */
package com.pigumer.tools.cli;

import com.pigumer.tools.download.Download;
import com.pigumer.tools.generator.Generator;
import com.pigumer.tools.initialize.Initializer;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.exit(new Main().command(args));
    }

    public int command(String[] args) {
        Options options = new Options();
        options.addOption("f", true, "file");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            String file = line.getOptionValue("f", "build.yml");
            for (String command: line.getArgs()) {
                switch (command) {
                    case "download":
                        download(file);
                        break;
                    case "init":
                        init(file);
                        break;
                    case "generate":
                        generate(file);
                        break;
                    default:
                }
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
            return -1;
        } catch (IOException e) {
            System.err.println("IO Error. Reason: " + e.getMessage());
            return -1;
        } catch (InterruptedException e) {
            System.err.println("Timeout. Reason: " + e.getMessage());
            return -1;
        }
        return 0;
    }

    public void download(String file) throws IOException, InterruptedException {
        System.out.println("download");
        Download download = new Download();
        download.download(file);
    }

    public void init(String file) throws IOException {
        System.out.println("init");
        Initializer initializer = new Initializer();
        initializer.initAll(file);
    }

    public void generate(String file) throws IOException, InterruptedException {
        System.out.println("generate");
        Generator generator = new Generator();
        generator.generateAll(file);
    }
}
