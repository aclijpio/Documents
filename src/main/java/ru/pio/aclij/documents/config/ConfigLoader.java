package ru.pio.aclij.documents.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    private static final String CONFIG_FILE = "config.yaml";

    private AppConfig appConfig;


    public static AppConfig loadConfig() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("config.yaml").getFile());
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        try {
            return om.readValue(file, AppConfig.class);
        }catch (IOException e){
            throw new AppConfigException("Failed to find resource", e);
        }
    }
}
