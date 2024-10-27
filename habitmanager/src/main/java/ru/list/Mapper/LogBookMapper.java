package ru.list.Mapper;

import java.util.List;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ru.list.DTO.LogBookResponse;
import ru.list.Model.LogBook;

public interface LogBookMapper {

    LogBookMapper INSTANCE = Mappers.getMapper(LogBookMapper.class);

    @Mapping(target = "habit_id", expression = "java(logBook.getHabit().getId())")
    LogBookResponse toHabitResponse(LogBook logBook);

    List<LogBookResponse> toListHabitResponse(List<LogBook> logBooks);

    @Mapping(target = "habit", expression = "java(null)")
    LogBook toLogBook(LogBookResponse logBookResponse);

    List<LogBook> toListLogBook(List<LogBookResponse> logBookResponses);

}
