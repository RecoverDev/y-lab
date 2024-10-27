package ru.list.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ru.list.DTO.PersonResponse;
import ru.list.Model.Person;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonResponse toPersonResponse(Person person);

    List<PersonResponse> toListPersonResponse(List<Person> persons);

}
