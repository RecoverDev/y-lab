package ru.list.Model;

/**
 * интерфейс предназначен для описания периодов выполнения привычек
 */
public interface PeriodDescription {
    /**
     * возвращает наименование периода на русском языке
     * @return - наименование
     */
    public String getDescription();
    /**
     * возвращает длительность периода
     * @return - длительность
     */
    public int getInterval();

}
