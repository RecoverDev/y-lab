package ru.list.Controller;

import java.util.ArrayList;
import java.util.List;

import ru.list.Observe;
import ru.list.In.AdminView;
import ru.list.In.PersonView;
import ru.list.Model.Person;
import ru.list.Service.PersonService;

public class AdminController implements ObserveController{
    private PersonService personService = null;
    private List<Observe> listener = new ArrayList<>();
    private AdminView adminView = new AdminView();
    private Person currentPerson = null;

    public AdminController(PersonService personService) {
        this.personService = personService;
    }

    public void showMenu() {
        int answer = adminView.showMenu();

        switch (answer) {
            case 1: //список пользователей
                showPersons();
                break;
        
            default:
                break;
        }
        this.observe(answer);
    }

    private void showPersons() {
        
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
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
