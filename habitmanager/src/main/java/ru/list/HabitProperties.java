package ru.list;

public class HabitProperties {
    private String url;
    private String user;
    private String password;
    private String changelogFile;

    private PropertiesReader reader = null;

    public HabitProperties(String fileName) {
        this.reader = new PropertiesReader(fileName);
    }

    public void load() {

        url = reader.readProperty("db.url");
        user = reader.readProperty("db.user");
        password = reader.readProperty("db.password");
        changelogFile = reader.readProperty("liquibae.changelog");
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
