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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.list.habitmanager.DTO.LogBookResponse;
import ru.list.habitmanager.Mapper.LogBookMapper;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Service.LogBookService;

@Tag(name = "LogBooks", description = "API по работе с записями о выполнении привычек")
@RestController
@RequestMapping("/logbooks")
@RequiredArgsConstructor
public class LogBookRestController {
    private final LogBookService service;
    private final LogBookMapper mapper;

    @Operation(summary = "Получение записей о выполнении привычек", tags = "LogBooks")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получены записи о выполнении привычек",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LogBookResponse.class)))})})
    @GetMapping
    public ResponseEntity<List<LogBookResponse>> getLogBooks() {
        List<LogBook> logBooks = service.getLogBooks();
        List<LogBookResponse> logBookResponses = mapper.toListLogBookResponse(logBooks);
        return new ResponseEntity<>(logBookResponses, HttpStatus.OK);
    }

    @Operation(summary = "Получение записи о выполнении привычки по ID", tags = "LogBooks")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получена запись о выполнении привычки",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LogBookResponse.class)))})})
    @GetMapping("/{id}")
    public ResponseEntity<LogBookResponse> getLogBookById(@PathVariable(name="id") int id) {
        LogBook result = service.getLogBookById(id);
        if (result != null) {
            LogBookResponse logBookResponse = mapper.toLogBookResponse(result);
            return new ResponseEntity<>(logBookResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Добавление новой записи о выполнении привычки", tags = "LogBooks")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Добавлена запись о выполнении привычки",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LogBookResponse.class)))})})
    @PostMapping
    public ResponseEntity<HttpStatus> addLogBook(@RequestBody LogBookResponse logBookResponse) {
        LogBook logBook = mapper.toLogBook(logBookResponse);
        boolean result = service.addLogBook(logBook);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Удаление записи о выполнении привычки по ID", tags = "LogBooks")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Удалена запись о выполнении привычки",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LogBookResponse.class)))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLogBook(@PathVariable(name="id") int id) {
        boolean result = service.deleteLogBookById(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(summary = "Получение записей о выполнении привычки по ID привычки", tags = "LogBooks")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список записей о выполнении привычки",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LogBookResponse.class)))})})
    @GetMapping("/habit/{id}")
    public ResponseEntity<List<LogBookResponse>> getLogBooksByHabit(@PathVariable(name="id") int id) {
        List<LogBook> logBooks = service.getLogBookByHabit(id);
        if (logBooks != null) {
            List<LogBookResponse> logBookResponses = mapper.toListLogBookResponse(logBooks);
            return new ResponseEntity<>(logBookResponses,HttpStatus.OK);
        } 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение записей о выполнении привычки по ID пользователя", tags = "LogBooks")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список записей о выполнении привычки",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LogBookResponse.class)))})})
    @GetMapping("/person/{id}")
    public ResponseEntity<List<LogBookResponse>> getLogBooksByPerson(@PathVariable(name="id") int id) {
        List<LogBook> logBooks = service.getLogBookByPerson(id);
        if (logBooks != null) {
            List<LogBookResponse> logBookResponses = mapper.toListLogBookResponse(logBooks);
            return new ResponseEntity<>(logBookResponses,HttpStatus.OK);
        } 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
