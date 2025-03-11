package ru.yandex.practicum.srpint4app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.srpint4app.dto.CommentDto;
import ru.yandex.practicum.srpint4app.model.DbComment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentDto dbToDto(DbComment dbComment);
    DbComment dtoToDb(CommentDto commentDto);
}
