package ru.list.Parameters;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadParameters {
    private String name;

    public ReadParameters(String name) {
        this.name = name;
    }

    public Parameters read()  {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Parameters parameters = null;
        try(FileReader fileReader = new FileReader(getClass().getResource("/" + name).toURI().getPath())) {
            parameters = mapper.readValue(fileReader, Parameters.class);
        } catch (IOException | URISyntaxException e) {
            log.error("Ошибка чтения файла %s".formatted(name), e);
        }
        return parameters;

    }

}
