package ru.list.habitmanager.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.list.habitmanager.DTO.PersonResponse;
import ru.list.habitmanager.Mapper.PersonMapper;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Service.PersonService;
import io.swagger.v3.oas.annotations.media.*;

@Tag(name = "Persons", description = "API по работе с пользователями")
@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonRestController {
    private final PersonService personService;
    private final PersonMapper mapper;

    @Operation(summary = "Получение списка пользователей", tags = "Persons")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список пользователей ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonResponse.class)))})})
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getPersons() {
        List<Person> persons = personService.getPersons();
        List<PersonResponse> personResponses = mapper.toListPersonResponse(persons);
        return personResponses != null && !personResponses.isEmpty() ?
                new ResponseEntity<>(personResponses, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение пользователя по ID", tags = "Persons")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен пользователь",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonResponse.class)))})})
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable(name="id") int id) {
        Person person = personService.getPersonById(id);
        if (person != null) {
            PersonResponse personResponse = mapper.toPersonResponse(person);
            return new ResponseEntity<>(personResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление нового пользователя", tags = "Persons")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Пользователь успешно добавлен ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonResponse.class)))})})
    @PostMapping
    public ResponseEntity<HttpStatus> addPerson(@RequestBody PersonResponse personResponse) {
        Person person = mapper.toPerson(personResponse);
        boolean result = personService.addPerson(person);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление пользователя", tags = "Persons")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Пользователь успешно удален",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonResponse.class)))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable(name="id") int id) {
        boolean result = personService.deletePerson(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
