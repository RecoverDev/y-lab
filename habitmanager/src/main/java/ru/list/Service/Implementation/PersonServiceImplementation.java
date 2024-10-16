package ru.list.Service.Implementation;

import java.util.List;

import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;
import ru.list.Service.PersonService;

public class PersonServiceImplementation implements PersonService {
    private PersonRepository repository = null;

    public PersonServiceImplementation(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addPerson(Person person) {
        if (repository.findByPassword(person.getPassword()) == null) {
            return repository.save(person);
        }
        return false;

    }

    @Override
    public boolean deletePerson(Person person) {
        return repository.delete(person);
    }

    @Override
    public void editPerson(Person person) {
        if (repository.exist(person)) {
            repository.delete(person);
        }
        repository.save(person);
    }


    @Override
    public List<Person> getPersons() {
        return repository.findAll();
    }

}
