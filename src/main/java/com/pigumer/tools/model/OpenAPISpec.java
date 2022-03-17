/*
 * @author Shigeki Shoji
 */
package com.pigumer.tools.model;

public class OpenAPISpec {

    private final String name;
    private final String infoVersion;

    public OpenAPISpec(String name, String infoVersion) {
        this.name = name;
        this.infoVersion = infoVersion;
    }

    public String getName() {
        return name;
    }

    public String getInfoVersion() {
        return infoVersion;
    }
}
