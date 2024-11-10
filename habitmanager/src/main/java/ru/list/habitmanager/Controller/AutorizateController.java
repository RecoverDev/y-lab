package ru.list.habitmanager.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.list.habitmanager.DTO.PersonResponse;
import ru.list.habitmanager.Mapper.PersonMapper;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.SingRespose;
import ru.list.habitmanager.Service.PersonService;

@RestController
@RequiredArgsConstructor
public class AutorizateController {
    private final PersonService service;
    private final PersonMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid SingRespose request) {
        boolean result = service.authentication(request);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid PersonResponse personResponse) {
        Person person = mapper.toPerson(personResponse);
        boolean result = service.addPerson(person);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
