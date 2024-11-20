package ru.list.habitmanager.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import ru.list.habitmanager.DTO.HabitResponse;
import ru.list.habitmanager.DTO.LogBookResponse;
import ru.list.habitmanager.Mapper.HabitMapper;
import ru.list.habitmanager.Mapper.LogBookMapper;
import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Service.StatisticService;

@Tag(name = "Statistic", description = "Возвращает статистику выполнения привычек пользователем")
@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticRestController {
    private final StatisticService statisticService;
    private final LogBookMapper mapper;
    private final HabitMapper habitMapper;

    @Operation(summary = "Получение непрерывной последовательности выполнения привычек пользователя", tags = "Statistic")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен непрерывная последовательность выполнения привычек пользователя ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LogBookResponse.class)))})})
    @GetMapping("/streak/{id}")
    public ResponseEntity<List<LogBookResponse>> getStreakByPerson(@PathVariable(name="id") int id){
        List<LogBook> logBooks = statisticService.streakHabits(id);
        List<LogBookResponse> logBookResponses = mapper.toListLogBookResponse(logBooks);
        return logBookResponses.size() > 0 ? new ResponseEntity<>(logBookResponses, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получение процента выполнения привычек пользователя", tags = "Statistic")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен процент выполнения привычек пользователя ",
                                        content = {@Content(mediaType = "Double",
                                    array = @ArraySchema(schema = @Schema(implementation = Double.class)))})})
    @GetMapping("/percent/{id}")
    public ResponseEntity<Double> getPercentByPerson(@PathVariable(name="id") int id) {
        double percent = statisticService.percentSuccess(id);
        return new ResponseEntity<>(percent, HttpStatus.OK);
    }

    @Operation(summary = "Получение списка записей выполнения привычек за несколько последних дней пользователя", tags = "Statistic")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен список записей выполнения привычек пользователя ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LogBookResponse.class)))})})
    @GetMapping("/execution/{id}/{days}")
    public ResponseEntity<List<LogBookResponse>> getExecutionHabitByPerson(@PathVariable(name="id") int id, @PathVariable(name="days") int days) {
        List<LogBook> logBooks = statisticService.executionHabit(id, days);
        List<LogBookResponse> logBookResponses = mapper.toListLogBookResponse(logBooks);
        return logBookResponses.size() > 0 ? new ResponseEntity<>(logBookResponses, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "Получение прогресса выполнения привычек пользователя", tags = "Statistic")
    @ApiResponses(value = {@ApiResponse(responseCode =  "200", 
                                        description = "Получен прогресс выполнения привычек пользователя ",
                                        content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = HabitResponse.class)))})})
    @GetMapping("/progress/{id}")
    public ResponseEntity<Map<HabitResponse, Long>> getprogressHabitByPerson(@PathVariable(name="id") int id) {
        Map<Habit,Long> progress = statisticService.progressHabit(id);
        Map<HabitResponse,Long> habitResponses = new HashMap<>();
        for (Map.Entry<Habit,Long> entry : progress.entrySet()) {
            HabitResponse habitResponse = habitMapper.toHabitResponse(entry.getKey());
            habitResponses.put(habitResponse, entry.getValue());
        }
        return habitResponses.size() >0 ? new ResponseEntity<>(habitResponses,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
