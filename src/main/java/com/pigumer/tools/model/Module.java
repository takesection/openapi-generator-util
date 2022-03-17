/*
 * @author Shigeki Shoji
 */
package com.pigumer.tools.model;

import java.util.Map;

public class Module {

    private String name;
    private String options;
    private Map<String, Object> template;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Map<String, Object> getTemplate() {
        return template;
    }

    public void setTemplate(Map<String, Object> template) {
        this.template = template;
    }
}
