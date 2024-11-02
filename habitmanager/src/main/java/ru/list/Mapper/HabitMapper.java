package ru.list.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.list.DTO.HabitResponse;
import ru.list.Model.Habit;

@Mapper(componentModel = "spring")
public interface HabitMapper {

    @Mapping(target = "period_id", expression = "java(habit.getPeriod().ordinal())")
    @Mapping(target = "person_id", expression = "java(habit.getPerson().getId())")
    HabitResponse toHabitResponse(Habit habit);

    List<HabitResponse> toListHabitResponse(List<Habit> habits);

    @Mapping(target = "period", expression = "java(ru.list.Model.Period.values()[habitResponse.getPeriod_id()])")
    @Mapping(target = "person", expression = "java(null)")
    Habit toHabit(HabitResponse habitResponse);

    List<Habit> toListHabit(List<HabitResponse> habitResponses);
}
