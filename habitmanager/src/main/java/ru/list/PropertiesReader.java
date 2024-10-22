package ru.list;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class PropertiesReader {
    private String nameFile;

    public PropertiesReader(String nameFile) {
        this.nameFile = nameFile;
    }

    public String readProperty(String nameProperty) {
        String result = "";
        Properties properties = new Properties();
        try (FileReader reader =  new FileReader(getClass().getResource("/" + nameFile).toURI().getPath())) {
            properties.load(reader);
            result = properties.getProperty(nameProperty);
        } catch (IOException | URISyntaxException e ) {
            System.out.println("Ошибка получения значения ключа из файла " + nameFile + ": " + e.getMessage());
        }
        return result;
    }


}
