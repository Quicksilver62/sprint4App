package ru.yandex.practicum.srpint4app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Integer id;
    private String title;
    private String body;
    private String picture;
    List<CommentDto> commentsCount;
    Integer likesCount;
    List<TagDto> tags;
    private LocalDateTime createdAt;
}
