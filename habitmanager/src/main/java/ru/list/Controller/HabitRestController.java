package ru.list.Controller;

import ru.list.Principal;
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

import ru.list.DTO.HabitResponse;
import ru.list.Mapper.HabitMapper;
import ru.list.Model.Habit;
import ru.list.Model.Person;
import ru.list.Service.HabitService;
import ru.list.Service.PersonService;

@RestController
@RequestMapping("/habits")
public class HabitRestController {
    private HabitService habitService = null;
    private PersonService personService = null;
    private HabitMapper mapper = null;
    private Person currentPerson = null;

    public HabitRestController(HabitService habitService, PersonService personService, HabitMapper mapper, Principal principal) {
        this.habitService = habitService;
        this.personService = personService;
        this.mapper = mapper;
        this.currentPerson = principal.getCurrentPerson();
    }

    @GetMapping()
    public ResponseEntity<List<HabitResponse>> getHabitByPerson() {
        if (currentPerson == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Habit> habits = habitService.getHabitsByPerson(currentPerson);
        List<HabitResponse> habitResponses = mapper.toListHabitResponse(habits);
        return new ResponseEntity<>(habitResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitResponse> getHabitById(@PathVariable(name="id") int id) {
        Habit habit = habitService.getById(id);
        if (habit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HabitResponse habitResponse = mapper.toHabitResponse(habit);
        return new ResponseEntity<>(habitResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addHabit(@RequestBody HabitResponse habitResponse) {
        Habit habit = mapper.toHabit(habitResponse);
        Person person = personService.getPersonById(habitResponse.getPerson_id());
        habit.setPerson(person);
        boolean result =  habitService.addHabit(habit);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHabit(@PathVariable(name="id") int id) {
        boolean result = habitService.deleteHabit(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
