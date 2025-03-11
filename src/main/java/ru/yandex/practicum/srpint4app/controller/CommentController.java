package ru.yandex.practicum.srpint4app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.srpint4app.dto.CommentDto;
import ru.yandex.practicum.srpint4app.service.CommentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String createOrUpdateComment(@ModelAttribute CommentDto comment) {
      commentService.createOrUpdateComment(comment);
      return "redirect:/posts";
    }

    @DeleteMapping(value = "{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String deleteComment(@PathVariable Integer id) {
      commentService.deleteComment(id);
      return "redirect:/posts";
    }
}
