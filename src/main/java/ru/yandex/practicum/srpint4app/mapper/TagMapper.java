package ru.yandex.practicum.srpint4app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.srpint4app.dto.TagDto;
import ru.yandex.practicum.srpint4app.model.DbTag;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    TagDto dbToDto(DbTag dbTag);
    DbTag dtoToDb(TagDto tagDto);
}
