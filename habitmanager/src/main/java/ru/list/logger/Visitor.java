package ru.list.logger;

import java.util.List;

/**
 * Интерфейс описывает шаблон Посетитель
 */
public interface Visitor {
    void out(List<String> data);
}
