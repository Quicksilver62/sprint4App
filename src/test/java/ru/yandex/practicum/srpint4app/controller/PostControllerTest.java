package ru.yandex.practicum.srpint4app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.srpint4app.dto.CommentDto;
import ru.yandex.practicum.srpint4app.dto.PostDto;
import ru.yandex.practicum.srpint4app.dto.PostPreviewDto;
import ru.yandex.practicum.srpint4app.dto.TagDto;
import ru.yandex.practicum.srpint4app.service.PostService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @BeforeEach
    public void setup() {
        var posts = List.of(
                PostPreviewDto.builder()
                        .id(1)
                        .title("Lorem")
                        .preview("Lorem ipsum dolor sit amet")
                        .picture("https://example.com/image.jpg")
                        .commentsCount(1)
                        .likesCount(2)
                        .tags(List.of())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        var post = PostDto.builder()
                .id(1)
                .title("My First Post")
                .body("This is the content of my first post.")
                .picture("https://example.com/image.jpg")
                .likesCount(10)
                .commentsCount(List.of(
                        new CommentDto(1, "Great post!", "Alice"),
                        new CommentDto(2, "I learned a lot.", "Bob")
                ))
                .tags(List.of(
                        new TagDto(1, "Spring Boot"),
                        new TagDto(2, "Thymeleaf")
                ))
                .createdAt(LocalDateTime.now())
                .build();

        when(postService.getAllPosts(Pageable.ofSize(10))).thenReturn(new SliceImpl<>(posts));
        when(postService.getPostById(1)).thenReturn(post);
        when(postService.likePost(1)).thenReturn(post);
    }

    @Test
    void getPosts_shouldReturnHtmlWithPosts() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts/list"));
    }

    @Test
    void getPostById_shouldReturnHtmlWithPost() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts/detail"));
    }

    @Test
    void likePostById_shouldReturnHtmlWithPost() throws Exception {
        mockMvc.perform(get("/posts/1/like"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts/detail"));
    }

    @Test
    void save_shouldAddUserToDatabaseAndRedirect() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "picture", // Имя параметра (должно совпадать с @RequestParam)
                "test.jpg", // Имя файла
                MediaType.IMAGE_JPEG_VALUE, // Тип файла
                "Test file content".getBytes() // Содержимое файла
        );

        mockMvc.perform(multipart("/posts")
                        .file(file)
                        .param("id", "1")
                        .param("title", "Test Title")
                        .param("picture", "")
                        .param("content", "Test Content")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"));
    }

    @Test
    void delete_shouldRemovePostFromDatabaseAndRedirect() throws Exception {
        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }
}
