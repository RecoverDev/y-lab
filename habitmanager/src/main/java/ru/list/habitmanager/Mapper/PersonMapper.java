package ru.list.habitmanager.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.list.habitmanager.DTO.PersonResponse;
import ru.list.habitmanager.Model.Person;


@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonResponse toPersonResponse(Person person);

    List<PersonResponse> toListPersonResponse(List<Person> persons);

    @Mapping(target = "password", expression = "java(personResponse.getEmail() + \"_1234\")")
    Person toPerson(PersonResponse personResponse);

    List<Person> toListPersons(List<PersonResponse> personResponses);

}
