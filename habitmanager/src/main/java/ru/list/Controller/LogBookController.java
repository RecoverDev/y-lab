package ru.list.Controller;

import java.util.ArrayList;
import java.util.List;

import ru.list.Observe;
import ru.list.DTO.JsonConverter;
import ru.list.DTO.LogBookResponse;
import ru.list.Mapper.LogBookMapper;
import ru.list.Model.LogBook;
import ru.list.Model.Person;
import ru.list.Service.HabitService;
import ru.list.Service.LogBookService;
import ru.list.logger.Logger;

public class LogBookController implements ObserveController{
    private Person currentPerson = null;
    private List<Observe> listener = new ArrayList<>();
    private LogBookMapper mapper = LogBookMapper.INSTANCE;
    private LogBookService logBookService = null;
    private HabitService habitService = null;
    private Logger logger = null;

    public LogBookController(LogBookService logBookService, HabitService habitService) {
        this.logBookService = logBookService;
        this.habitService = habitService;
        this.logger = logger;
    }

    /**
     * Получение списка записей по привычке
     * @param id - идентификатор привычки
     * @return - список записей в формате JSON
     */
    public String getLogBooks(int id) {
        List<LogBook> logBooks = logBookService.getLogBookByHabit(null);
        List<LogBookResponse> logBookResponses = mapper.toListHabitResponse(logBooks);
        JsonConverter<List<LogBookResponse>> converter = new JsonConverter<>(logger);
        return converter.fromObject(logBookResponses);
    }

    public boolean addLogbook(String jsonLogbook) {
        JsonConverter<LogBookResponse> converter = new JsonConverter<>(logger);
        LogBookResponse logBookResponse = converter.toObject(jsonLogbook, LogBookResponse.class);
        LogBook logBook = mapper.toLogBook(logBookResponse);
        logBook.setHabit(habitService.getById(logBookResponse.getHabit_id()));
        return logBookService.addLogBook(logBook);
    }

    @Override
    public void observe(Object o) {
        for (Observe observe : listener) {
            observe.observe(o);
        }
    }

    @Override
    public void addListener(Observe observe) {
        this.listener.add(observe);
    }

    @Override
    public void removeListener(Observe observe) {
        this.listener.remove(observe);
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

}
