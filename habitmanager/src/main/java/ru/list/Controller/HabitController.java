package ru.list.Controller;

import java.util.ArrayList;
import java.util.List;

import ru.list.Observe;
import ru.list.DTO.HabitResponse;
import ru.list.DTO.JsonConverter;
import ru.list.Mapper.HabitMaper;
import ru.list.Model.Habit;
import ru.list.Model.Person;
import ru.list.Service.HabitService;
import ru.list.Service.PersonService;
import ru.list.logger.Logger;

public class HabitController implements ObserveController{
    private Person currentPerson = null;
    private List<Observe> listener = new ArrayList<>();
    private HabitService habitService = null;
    private PersonService personService = null;
    private HabitMaper mapper = HabitMaper.INSTANCE;
    private Logger logger = null;

    public HabitController(HabitService habitService, PersonService personService, Logger logger) {
        this.habitService = habitService;
        this.personService = personService;
        this.logger = logger;

    }

    /**
     * Получение списка привычек определенного пользователя
     * @return - список привычек в формате JSON
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String getHabits() {
        List<Habit> habits = habitService.getHabitsByPerson(currentPerson);
        List<HabitResponse> habitResponses = mapper.toListHabitResponse(habits);
        JsonConverter converter = new JsonConverter<HabitResponse>(logger);
        return converter.fromObject(habitResponses);
    }

    /**
     * Добавление новой привычки
     * @return - результат добавления (true - успех/false - не успех)
     */
    public boolean addHabit(String jsonHabit) {
        JsonConverter<HabitResponse> converter = new JsonConverter<>(logger);
        HabitResponse habitResponse = (HabitResponse) converter.toObject(jsonHabit, HabitResponse.class);
        Habit habit = mapper.toHabit(habitResponse);
        Person person = personService.getPersonById(habitResponse.getPerson_id());
        habit.setPerson(person);
        return habitService.addHabit(habit);
    }

    /**
     * Удаление привычки
     * @param id - идентификатор привычки
     * @return - результат добавления (true - успех/false - не успех)
     */
    public boolean deleteHabit(int id) {
        return habitService.deleteHabit(id);
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
