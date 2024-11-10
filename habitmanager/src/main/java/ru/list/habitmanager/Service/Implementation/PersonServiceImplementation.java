package ru.list.habitmanager.Service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.list.aspect_starter.Annotation.EnableXXX;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.SingRespose;
import ru.list.habitmanager.Repository.PersonRepository;
import ru.list.habitmanager.Service.PersonService;

@Service
@RequiredArgsConstructor
public class PersonServiceImplementation implements PersonService {
    private final PersonRepository repository;
    private Person currentPerson = null;

    @EnableXXX
    @Override
    public boolean addPerson(Person person) {
        if (repository.findByPassword(person.getPassword()) == null) {
            return repository.save(person);
        }
        return false;

    }

    @Override
    public boolean deletePerson(int id) {
        return repository.delete(id);
    }

    @Override
    public void editPerson(Person person) {
        if (repository.exist(person)) {
            repository.delete(person.getId());
        }
        repository.save(person);
    }


    @EnableXXX
    @Override
    public List<Person> getPersons() {
        return (List<Person>) repository.findAll();
    }

    @EnableXXX
    @Override
    public Person getPersonById(int id) {
       return repository.findById(id);
    }

    @EnableXXX
    @Override
    public Person getPersonByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

    @Override
    public Person getCurrentPerson() {
        return this.currentPerson;
    }

    @EnableXXX
    @Override
    public boolean authentication(SingRespose respose) {
        Person person = repository.findByEmailAndPassword(respose.getEmail(),respose.getPassword());
        this.currentPerson = person;
        return person != null;
    }    

}
