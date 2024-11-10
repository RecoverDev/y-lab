package ru.list.habitmanager.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ru.list.habitmanager.DTO.LogBookResponse;
import ru.list.habitmanager.Model.LogBook;

@Mapper(componentModel = "spring")
public interface LogBookMapper {

    LogBookMapper INSTANCE = Mappers.getMapper(LogBookMapper.class);

    @Mapping(target = "habit_id", expression = "java(logBook.getHabit().getId())")
    LogBookResponse toLogBookResponse(LogBook logBook);

    List<LogBookResponse> toListLogBookResponse(List<LogBook> logBooks);

    @Mapping(target = "habit", expression = "java(null)")
    LogBook toLogBook(LogBookResponse logBookResponse);

    List<LogBook> toListLogBook(List<LogBookResponse> logBookResponses);

}
