package ru.list.Controller;

import java.util.ArrayList;
import java.util.List;

import ru.list.Observe;
import ru.list.Model.Person;
import ru.list.Service.PersonService;

public class AutorizationController implements ObserveController{
    private PersonService personService = null;

    public AutorizationController(PersonService personService) {
        this.personService = personService;
    }

    private List<Observe> listener = new ArrayList<>();

    /**
     * Авторизация пользователя
     * @param email - E-mail пользователя
     * @param password - пароль пользователя
     * @return - результат добавления (true - успех/false - не успех)
     */
    public boolean login(String email, String password) {
        if (email == null) {
            return false;
        }
        if (email.isEmpty()) {
            return false;
        }
        if (password == null) {
            return false;
        }

        Person person = personService.getPersonByEmailAndPassword(email, password);
        if (person == null) {
            return false;
        }
        observe(person);
        return true;

    }

    /**
     * выход пользователя из системы
     */
    public void logout() {
        observe(null);
    }

    @Override
    public void observe(Object o) {
        for (Observe observe : listener) {
            observe.observe(o);
        }
    }

    @Override
    public void addListener(Observe observe) {
        listener.add(observe);
    }

    @Override
    public void removeListener(Observe observe) {
        listener.remove(observe);
    }

}
