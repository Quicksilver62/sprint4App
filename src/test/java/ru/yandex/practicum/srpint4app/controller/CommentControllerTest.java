package ru.yandex.practicum.srpint4app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.srpint4app.repository.CommentRepository;
import ru.yandex.practicum.srpint4app.service.CommentService;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @Test
    void save_shouldSaveCommentToDatabaseAndRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/comment")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("text", "Lorem ipsum dolor sit amet")
                        .param("author", "John Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void delete_shouldRemoveCommentFromDatabaseAndRedirect() throws Exception {
        mockMvc.perform(delete("/posts/comment/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }
}
