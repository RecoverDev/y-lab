package ru.list.Controller;

import java.util.ArrayList;
import java.util.List;

import ru.list.Observe;
import ru.list.In.AutorizateView;
import ru.list.Model.Person;
import ru.list.Service.AutorizationService;
import ru.list.Service.PersonService;

public class AutorizationController implements ObserveController{
    private AutorizationService autorizationService = null;
    private PersonService personService = null;
    private AutorizateView autorizateView = new AutorizateView();

    public AutorizationController(AutorizationService autorizationService, PersonService personService) {
        this.autorizationService = autorizationService;
        this.personService = personService;
    }

    private List<Observe> listener = new ArrayList<>();

    public Person startForm() {
        List<String> personalData = new ArrayList<>();
        Person person = null;
        int result = autorizateView.StartView();

        switch (result) {
            case 1:
                personalData = autorizateView.LoginView();
                person = autorizationService.autorizate(personalData.get(0), personalData.get(1));
                break;
            case 2:
                personalData = autorizateView.RegistrationView();
                if (personalData.size() == 4) {
                    Person newPerson = new Person(personalData.get(0), personalData.get(1), personalData.get(2), Integer.parseInt(personalData.get(3)),true);
                    personService.addPerson(newPerson);
                }
                break;
            default:
                break;
        }
        this.observe(result);
        return person;
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
