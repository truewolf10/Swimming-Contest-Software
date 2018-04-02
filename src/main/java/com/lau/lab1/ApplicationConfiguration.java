package com.lau.lab1;

import java.io.IOException;
import java.util.Properties;

public class ApplicationConfiguration {
    private static final String PROPERTIES_FILE = "/config.properties";
    private static final String DB_SERVER = "dbServer";
    private static final String DB_USER = "dbUser";
    private static final String DB_PASSWORD = "dbPassword";


    private static ApplicationConfiguration instace;
    private Properties properties;

    public static ApplicationConfiguration getInstace() throws IOException {
        if (instace == null)
            instace = new ApplicationConfiguration();
        return instace;
    }

    private ApplicationConfiguration() throws IOException {
        properties = new Properties();
        properties.load(getClass().getResourceAsStream(PROPERTIES_FILE));
    }

    public String getDatabaseServer() {
        return properties.getProperty(DB_SERVER);
    }

    public String getDatabaseUser() {
        return properties.getProperty(DB_USER);
    }

    public String getDatabasePassword() {
        return properties.getProperty(DB_PASSWORD);
    }
}
