package ru.pio.aclij.documents.financial.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    private static final String CONFIG_FILE = "config.yaml";

    private AppConfig appConfig;


    public static AppConfig loadConfig() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("config.yaml").getFile());

        ObjectMapper om = new ObjectMapper(new YAMLFactory());

        AppConfig employee = om.readValue(file, AppConfig.class);

        System.out.println("Employee info " + employee.toString());

        System.out.println("Accessing first element: " + employee);

        return employee;
    }
}
