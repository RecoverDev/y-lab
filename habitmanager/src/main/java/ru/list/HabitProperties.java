package ru.list;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class HabitProperties {
    private String url;
    private String user;
    private String password;
    private String changelogFile;

    private String fileName;

    public HabitProperties(String fileName) {
        this.fileName = fileName;
    }

    public boolean load() {
        Properties properties = new Properties();
        boolean result = false;

        try (FileReader reader =  new FileReader(getClass().getResource("/" + fileName).toURI().getPath())) {
            properties.load(reader);
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            changelogFile = properties.getProperty("liquibase.changelog");
            result = true;

        } catch (IOException | URISyntaxException e ) {
            
        }

        return result;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getChangelogFile() {
        return changelogFile;
    }


}
