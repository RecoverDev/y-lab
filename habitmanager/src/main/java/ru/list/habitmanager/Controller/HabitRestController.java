package ru.list.habitmanager.Controller;

import ru.list.habitmanager.DTO.HabitResponse;
import ru.list.habitmanager.Mapper.HabitMapper;
import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Service.HabitService;
import ru.list.habitmanager.Service.PersonService;

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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Habits", description = "API по работе с привычками пользователей")
@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitRestController {
    private final HabitService habitService;
    private final PersonService personService;
    private final HabitMapper mapper;

    @Operation(summary = "Получение списка привычек пользователя", tags = "Habits")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список привычек пользователя ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = HabitResponse.class)))})})
    @GetMapping
    public ResponseEntity<List<HabitResponse>> getHabitByPerson() {
        Person currentPerson = personService.getCurrentPerson();
        if (currentPerson == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Habit> habits = habitService.getHabitsByPerson(currentPerson);
        List<HabitResponse> habitResponses = mapper.toListHabitResponse(habits);
        return new ResponseEntity<>(habitResponses, HttpStatus.OK);
    }

    @Operation(summary = "Получение привычки пользователя по ID", tags = "Habits")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получена привычка пользователя ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = HabitResponse.class)))})})
    @GetMapping("/{id}")
    public ResponseEntity<HabitResponse> getHabitById(@PathVariable(name="id") int id) {
        Habit habit = habitService.getById(id);
        if (habit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HabitResponse habitResponse = mapper.toHabitResponse(habit);
        return new ResponseEntity<>(habitResponse,HttpStatus.OK);
    }

    @Operation(summary = "Добавление новой привычки пользователя", tags = "Habits")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Новая привычка успешно добавлена",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = HabitResponse.class)))})})
    @PostMapping
    public ResponseEntity<HttpStatus> addHabit(@RequestBody HabitResponse habitResponse) {
        Habit habit = mapper.toHabit(habitResponse);
        Person person = personService.getPersonById(habitResponse.getPerson_id());
        habit.setPerson(person);
        boolean result =  habitService.addHabit(habit);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @Operation(summary = "Удаление привычки пользователя", tags = "Habits")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "привычки успешно удалена",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = HabitResponse.class)))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHabit(@PathVariable(name="id") int id) {
        boolean result = habitService.deleteHabit(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
