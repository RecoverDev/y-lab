package ru.list.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.list.Observe;
import ru.list.DTO.JsonConverter;
import ru.list.DTO.PersonResponse;
import ru.list.Mapper.PersonMapper;
import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Out.PersonView;
import ru.list.Service.HabitService;
import ru.list.Service.LogBookService;
import ru.list.Service.PersonService;
import ru.list.Service.StatisticService;
import ru.list.logger.Logger;

/**
 * Контроллер описывает действия пользователя
 */
public class PersonController implements ObserveController {
    private PersonService personService = null;
    private HabitService habitService = null;
    private LogBookService logBookService = null;
    private List<Observe> listener = new ArrayList<>();
    private PersonView personView = new PersonView();
    private Person currentPerson = null;
    private StatisticController statisticController = null;
    private Logger logger = null;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public void setCurrentPerson(Person currentPerson, Logger logger) {
        this.currentPerson = currentPerson;
    }

    public PersonController(PersonService personService, HabitService habitService, LogBookService logBookService, StatisticService statisticService){
        this.personService = personService;
        this.habitService = habitService;
        this.logBookService = logBookService;
        this.statisticController = new StatisticController(currentPerson, statisticService);
        this.logger = logger;
    }

    /**
     * получение пользователя по ID
     * @param id - идентификатор пользователя
     * @return - пользователь в формате JSON
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String getPersonById(int id) {
        Person person = personService.getPersonById(id);
        JsonConverter converter = new JsonConverter<Person>(logger);

        return converter.fromObject(personMapper.toPersonResponse(person));
    }

    /**
     * Получение списка пользователей
     * @return - список пользователей в формате JSON
     */
    @SuppressWarnings("unchecked")
    public String getPersons() {
        List<Person> persons = personService.getPersons();
        @SuppressWarnings("rawtypes")
        JsonConverter converter = new JsonConverter<Person>(logger);
        return converter.fromObject(personMapper.toListPersonResponse(persons));
    }

    /**
     * Добавление пользователя
     * @param personJson - добавляемый пользователь в формате JSON
     * @return - результат добавления (true - успех/false - не успех)
     */
    @SuppressWarnings("rawtypes")
    public boolean addPerson(String personJson) {
        JsonConverter converter = new JsonConverter<Person>(logger);
        @SuppressWarnings("unchecked")
        Person person = (Person) converter.toObject(personJson, Person.class);
        return personService.addPerson(person);
    }

    public boolean deletePerson(int id) {
        return personService.deletePerson(id);
    }


    /**
     * Выводит список привычек пользователя
     */
    private void showHabits() {
        int answer = personView.selectFilterView();

        switch (answer) {
            case 1 -> showHabitsByDate();
            case 2 -> showHabitsByPeriod();
        }

    }

    private void showHabitsByDate() {
        LocalDate date = personView.getDate();

        List<Habit> habits = habitService.getHabitsByPerson(currentPerson);
        List<Habit> habitByDate = habits.stream().filter(h -> h.getRegistration().equals(date)).toList();
        List<String> strHabits = habitByDate.stream().map(h -> String.format("%s - %s",h.getName(),h.getPeriod().getDescription())).toList();
        personView.showHabits(strHabits);
    }

    private void showHabitsByPeriod() {
        List<String> strPeriod = Arrays.stream(Period.values()).map(p -> p.toString()).toList();
        int nomPeriod = personView.choisePeriod(strPeriod);
        List<Habit> habits = habitService.getHabitsByPerson(currentPerson);
        List<Habit> habitByDate = habits.stream().filter(h -> h.getPeriod().equals(Period.values()[nomPeriod - 1])).toList();
        List<String> strHabits = habitByDate.stream().map(h -> String.format("%s - %s",h.getName(),h.getPeriod().getDescription())).toList();
        personView.showHabits(strHabits);
    }

    /**
     * Добавление новой привычки текущего пользователя
     */
    private void addHabit() {

        List<String> habitData;

        habitData = personView.addHabitView();
        habitService.addHabitFromString(currentPerson, habitData);
    }

    /**
     * удаление привычки пользователя
     */
    private void deleteHabit() {
        int position = personView.choiceHabit(habitService.getHabitByPersonAsString(currentPerson));
        habitService.deleteHabitByPosition(currentPerson, position);
    }

    /**
     * Добавление новой записи о выполнении привычки
     */
    private void addLogBook() {
        int position = personView.choiceHabit(habitService.getHabitByPersonAsString(currentPerson));
        logBookService.addLogBook(new LogBook(0,LocalDate.now(), habitService.getHAbitByPosition(currentPerson, position)));
    }

    private void personalAccount() {
        List<String> answer = personView.personalAccount();
        if (answer.size() == 3) {
            currentPerson.setName(answer.get(0));
            currentPerson.setEmail(answer.get(1));
            currentPerson.setPassword(answer.get(2));
            personService.editPerson(currentPerson);
        }

    }

    /**
     * Отображение статистики
     */
    private void showStatistic() {
        statisticController.setCurrentPerson(currentPerson);
        statisticController.showMenu();
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

}
