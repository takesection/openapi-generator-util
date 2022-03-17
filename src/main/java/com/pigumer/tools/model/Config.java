/*
 * @author Shigeki Shoji
 */
package com.pigumer.tools.model;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Config {

    // https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-Identifier
    private final Set<String> keywords = Set.of("abstract", "continue", "for", "new", "switch",
            "assert", "default", "if", "package", "synchronized",
            "boolean", "do", "goto", "private", "this",
            "break", "double", "implements", "protected", "throw",
            "byte", "else", "import", "public", "throws",
            "case", "enum", "instanceof", "return", "transient",
            "catch", "extends", "int", "short", "try",
            "char", "final", "interface", "static", "void",
            "class", "finally", "long", "strictfp", "volatile",
            "const", "float", "native", "super", "while", "_");
    private final Set<String> booleanLiteral = Set.of("true", "false");
    private final Set<String> nullLiteral = Set.of("null");

    private String version;
    private String openAPIGeneratorVersion;
    private String javaBasePackage;
    private String openAPISpec;
    private Map<String, String> properties;
    private List<Module> modules;

    /**
     * mavenRepositoryUrl.
     *
     * OpenAPI Generator を取得元の URL です。
     * 環境変数 "MAVEN_REPOSITORY_URL" の値、またはシステムプロパティ "MAVEN_REPOSITORY_URL" の値です。
     * いずれにも定義されていない場合のデフォルト値は "" です。
     *
     * @return mavenRepositoryUrl
     */
    public String getMavenRepositoryUrl() {
        String url = System.getenv("MAVEN_REPOSITORY_URL");
        if (url == null) {
            url = System.getProperty("MAVEN_REPOSITORY_URL", "https://repo1.maven.org/maven2");
        }
        return url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    protected void validatePackageName(String pkg) {
        if (pkg.startsWith(".") || pkg.endsWith(".")) {
            throw new RuntimeException("パッケージ名が不正です: " + pkg);
        }
        String[] pkgs = pkg.split("\\.");
        for (String identifier : pkgs) {
            if (identifier.isEmpty()) {
                throw new RuntimeException("パッケージ名に空文字列が含まれています");
            }
            if (keywords.contains(identifier) || booleanLiteral.contains(identifier) || nullLiteral.contains(identifier)) {
                throw new RuntimeException("パッケージ名に使用できないキーワードが含まれています: " + identifier);
            }
            if (!Character.isJavaIdentifierStart(identifier.charAt(0))) {
                throw new RuntimeException("パッケージ名に使用できない開始文字が含まれています: " + identifier);
            }
            for (int c : identifier.substring(1).toCharArray()) {
                if (!Character.isJavaIdentifierPart(c)) {
                    throw new RuntimeException("パッケージ名に使用できない文字が含まれています: " + identifier);
                }
            }
        }
    }

    public String getJavaBasePackage() {
        return javaBasePackage;
    }

    public void setJavaBasePackage(String javaBasePackage) {
        validatePackageName(javaBasePackage);
        this.javaBasePackage = javaBasePackage;
    }

    public String getOpenAPIGeneratorVersion() {
        return openAPIGeneratorVersion;
    }

    public void setOpenAPIGeneratorVersion(String openAPIGeneratorVersion) {
        this.openAPIGeneratorVersion = openAPIGeneratorVersion;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Map<String, String> getProperties() throws IOException {
        Map<String, String> props = new HashMap<>();
        props.putAll(properties);
        Map<String, String> env = System.getenv();
        props.putAll(env);
        if (javaBasePackage != null) {
            props.put("javaBasePackage", javaBasePackage);
        }
        OpenAPISpec spec = parseOpenAPISpec();
        props.put("openAPISpec", spec.getName());
        props.put("specVersion", spec.getInfoVersion());
        return props;
    }

    public void setProperties(Map<String, String> properties) {
        properties.put("now", DateTimeFormatter.ofPattern("uuuuMMddHHmmss").format(ZonedDateTime.now(ZoneId.of("UTC"))));
        this.properties = properties;
    }

    public String getOpenAPISpec() {
        return openAPISpec;
    }

    public void setOpenAPISpec(String openAPISpec) {
        this.openAPISpec = openAPISpec;
    }

    private OpenAPISpec parseOpenAPISpec() throws IOException {
        Yaml yaml = new Yaml();
        try (FileInputStream in = new FileInputStream(Paths.get(openAPISpec).toFile())) {
            Map<String, Object> map = yaml.loadAs(in, Map.class);
            return new OpenAPISpec(this.openAPISpec, (String) ((Map) map.get("info")).get("version"));
        }
    }
}
