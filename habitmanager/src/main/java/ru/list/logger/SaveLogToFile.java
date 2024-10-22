package ru.list.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Класс реализует шаблон Посетитель 
 * и предназначен для сохранения протокола в файл
 */
public class SaveLogToFile implements Visitor {

    @Override
    public void out(List<String> data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm");
        String nameFile = "log_" + LocalDateTime.now().format(formatter) + ".log";
        Path path = Paths.get(nameFile);
        try {
            Files.write(path, data);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл протокола работы программы: " + e.getMessage());
        }
    }

}
