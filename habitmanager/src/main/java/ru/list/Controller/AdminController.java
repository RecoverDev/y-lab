package ru.list.Controller;

import java.util.ArrayList;
import java.util.List;

import ru.list.Observe;
import ru.list.In.AdminView;
import ru.list.Model.Person;
import ru.list.Service.PersonService;

public class AdminController implements ObserveController{
    private PersonService personService = null;
    private List<Observe> listener = new ArrayList<>();
    private AdminView adminView = new AdminView();

    public AdminController(PersonService personService) {
        this.personService = personService;
    }

    public void showMenu() {
        int answer = adminView.showMenu();

        switch (answer) {
            case 1: //список пользователей
                showPersons();
                break;
            case 2: //удалить пользователя
                deletePerson();
                break;
            case 3: //блокировка пользователя
                blockedPerson();
                break;
            default:
                break;
        }
        this.observe(answer);
    }

    /**
     * Показать список пользователей
     */
    private void showPersons() {
        List<String> result = personService.getPersons()
                                           .stream()
                                           .map(p -> String.format("%s - %s",p.getName(),p.isBlocked() ? "доступ открыт" : "заблокирован")).toList();
        adminView.showPersons(result);
    }
    /** 
     * Удаление пользователя
     */
    private void deletePerson() {
        List<Person> persons = personService.getPersons();
        List<String> result = persons.stream().map(p -> String.format("%s - %s",p.getName(),p.isBlocked() ? "доступ открыт" : "заблокирован")).toList();
        int answer = adminView.choicePerson(result);
        if (answer > 0 & answer <= result.size()) {
            personService.deletePerson(persons.get(answer - 1));
        }
    }

    private void blockedPerson() {
        List<Person> persons = personService.getPersons();
        List<String> result = persons.stream().map(p -> String.format("%s - %s",p.getName(),p.isBlocked() ? "доступ открыт" : "заблокирован")).toList();
        int answer = adminView.choicePerson(result);
        if (answer > 0 & answer <= result.size()) {
            persons.get(answer - 1).setBlocked(false);
            personService.editPerson(persons.get(answer - 1));
        }
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
