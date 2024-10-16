package ru.list.Repository.Implementation;

import java.util.HashSet;
import java.util.List;

import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;

public class PersonRepositoryImplementation implements PersonRepository{
    private final HashSet<Person> repository = new HashSet<>();

    @Override
    public boolean save(Person person) {
        return repository.add(person);
    }

    @Override
    public boolean delete(Person person) {
        return repository.remove(person);
    }

    @Override
    public Person findByEmailAndPassword(String email, String password) {
        List<Person> result = repository.stream().filter(p -> (p.getEmail().equals(email) & p.getPassword().equals(password))).toList();
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<Person> findAll() {
        return repository.stream().toList();
    }

    @Override
    public Person findByPassword(String password) {
        List<Person> result = repository.stream().filter(p -> p.getPassword().equals(password)).toList();
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public boolean exist(Person person) {
        return repository.contains(person);
    }

}
