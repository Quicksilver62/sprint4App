package ru.yandex.practicum.srpint4app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.srpint4app.dto.PostDto;
import ru.yandex.practicum.srpint4app.dto.PostPreviewDto;
import ru.yandex.practicum.srpint4app.dto.TagDto;
import ru.yandex.practicum.srpint4app.model.DbPost;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "preview", expression = "java(getPreview(dbPost))")
    @Mapping(target = "commentsCount", expression = "java(getCommentCount(dbPost))")
    @Mapping(target = "tags", expression = "java(getTags(dbPost))")
    PostPreviewDto dbToDtoPreview(DbPost dbPost);

    PostDto dbToDto(DbPost dbPost);

    @Mapping(target = "tags", ignore = true)
    DbPost dtoToDb(PostDto postDto);

    default String getPreview(DbPost dbPost) {
        String body = dbPost.getBody();
        int maxLength = 3000;
        if (body.length() <= maxLength) {
          return body;
        }
        return body.substring(0, maxLength) + "...";
    }

    default Integer getCommentCount(DbPost dbPost) {
    return dbPost.getComments().size();
  }

    default List<TagDto> getTags(DbPost dbPost) {
        return dbPost.getTags().stream()
            .map(tag -> new TagDto(tag.getId(), tag.getName()))
            .collect(Collectors.toList());
    }
}
