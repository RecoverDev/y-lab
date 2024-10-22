package ru.list.logger;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * описывает запись журнала
 * date - дата/время записи (события)
 * message - описание события
 * result - результат события (true - успешно, false - неуспешно)
 */
@Data
@AllArgsConstructor
public class Record {
    private LocalDateTime date;
    private String message;
    private boolean result;

    /**
     * переопределен метод toString
     * 
     * @return String - строковое представление объекта
     */
    @Override
    public String toString() {
        return String.format("%s [%s] - %s", this.getDate().toString(),this.isResult() ? "ХОРОШО" : "ПЛОХО", this.getMessage());
    }
}
