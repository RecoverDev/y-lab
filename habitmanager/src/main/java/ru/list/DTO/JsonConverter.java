package ru.list.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.list.logger.Logger;

public class JsonConverter<T> {
    private Logger logger;

    public JsonConverter(Logger loger) {
        this.logger = logger;
    }

    public String fromObject(T object) {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            result =  mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.addRecord("Ошибка преобразования в JSON: " + e.getMessage(), false);
        }
        return result;
    }

    public T toObject(String string, Class<T> value) {
        ObjectMapper mapper = new ObjectMapper();
        T result = null;
        try {
            result = mapper.readValue(string, value );
        } catch (JsonProcessingException e) {
            logger.addRecord("Ошибка получения объекта из JSON: " + e.getMessage(), false);
        } 
        return result;
    }

}
