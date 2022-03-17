/*
 * @author Shigeki Shoji
 */
package com.pigumer.tools.download;

import com.pigumer.tools.model.Config;
import com.pigumer.tools.parser.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Download {

    public static final String name = "openapi-generator-cli.jar";

    public void download(String file) throws IOException, InterruptedException {
        try (FileInputStream in = new FileInputStream(Paths.get(file).toFile())) {
            Parser parser = new Parser();
            Config settings = parser.parse(in);
            String openApiGeneratorVersion = settings.getOpenAPIGeneratorVersion();
            String mavenRepositoryUrl = settings.getMavenRepositoryUrl();
            String url = String.format("%s/org/openapitools/openapi-generator-cli/%s/openapi-generator-cli-%s.jar", mavenRepositoryUrl, openApiGeneratorVersion, openApiGeneratorVersion);
            HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<byte[]> response = client.send(request, BodyHandlers.ofByteArray());
            if (response.statusCode() == 200) {
                Files.write(Paths.get(name), response.body());
            } else {
                throw new IOException("Error: status code = " + response.statusCode());
            }
        }
    }
}
