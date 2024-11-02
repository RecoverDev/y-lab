package ru.list.Service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.list.Annotation.Audit;
import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;
import ru.list.Service.PersonService;

@Service
public class PersonServiceImplementation implements PersonService {
    private PersonRepository repository = null;

    public PersonServiceImplementation(PersonRepository repository) {
        this.repository = repository;
    }

    @Audit
    @Override
    public boolean addPerson(Person person) {
        if (repository.findByPassword(person.getPassword()) == null) {
            return repository.save(person);
        }
        return false;

    }

    @Audit
    @Override
    public boolean deletePerson(int id) {
        return repository.delete(id);
    }

    @Audit
    @Override
    public void editPerson(Person person) {
        if (repository.exist(person)) {
            repository.delete(person.getId());
        }
        repository.save(person);
    }


    @Audit
    @Override
    public List<Person> getPersons() {
        return repository.findAll();
    }

    @Audit
    @Override
    public void editPersonFromString(Person person, List<String> data) {
        if (data.size() == 3) {
            person.setName(data.get(0));
            person.setEmail(data.get(1));
            person.setPassword(data.get(2));
            this.editPerson(person);
        }
    }

    @Audit
    @Override
    public Person getPersonById(int id) {
        List<Person> result =  repository.findAll().stream().filter(p -> p.getId() == id).toList();
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Audit
    @Override
    public Person getPersonByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

}
