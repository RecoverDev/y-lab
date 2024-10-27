package ru.list.Controller;

import java.util.ArrayList;
import java.util.List;

import ru.list.Observe;
import ru.list.DTO.JsonConverter;
import ru.list.Mapper.PersonMapper;
import ru.list.Model.Person;
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
    private List<Observe> listener = new ArrayList<>();
    private Logger logger = null;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;


    public PersonController(PersonService personService, HabitService habitService, LogBookService logBookService, StatisticService statisticService){
        this.personService = personService;
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
