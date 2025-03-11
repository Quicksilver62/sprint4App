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
public class PostPreviewDto {
    private Integer id;
    private String title;
    private String preview;
    private String picture;
    Integer commentsCount;
    Integer likesCount;
    List<TagDto> tags;
    private LocalDateTime createdAt;
}
