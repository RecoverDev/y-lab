package ru.list.Service.Implementation;

import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;
import ru.list.Service.AutorizationService;

public class AutorizationServiceImplementation implements AutorizationService {
    private PersonRepository repository = null;

    public AutorizationServiceImplementation(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person autorizate(String login, String password) {
        return repository.findByEmailAndPassword(login, password);
    }

    @Override
    public Person out() {
        return null;
    }

}
