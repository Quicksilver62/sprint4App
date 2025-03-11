package ru.yandex.practicum.srpint4app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.srpint4app.dto.PostDto;
import ru.yandex.practicum.srpint4app.dto.PostPreviewDto;
import ru.yandex.practicum.srpint4app.service.PostService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String getAllPosts(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Slice<PostPreviewDto> posts = postService.getAllPosts(pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("post", new PostPreviewDto());
        return "posts/list";
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
        public String getPostById(@PathVariable Integer id, Model model) {
        PostDto post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }

    @GetMapping(value = "/{id}/like", produces = MediaType.TEXT_HTML_VALUE)
    public String getPostById(@PathVariable Integer id) {
        postService.likePost(id);
        return "posts/detail";
    }

    @PostMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String createOrUpdatePost(@RequestBody PostDto post, @RequestParam("picture") MultipartFile file) {
      postService.createOrUpdatePost(post, file);
      return "Post created/updated successfully";
    }

    @DeleteMapping(value = "/{id}")
    public String deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
