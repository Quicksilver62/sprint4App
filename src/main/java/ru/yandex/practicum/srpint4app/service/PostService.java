package ru.yandex.practicum.srpint4app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.srpint4app.dto.PostDto;
import ru.yandex.practicum.srpint4app.dto.PostPreviewDto;
import ru.yandex.practicum.srpint4app.mapper.PostMapper;
import ru.yandex.practicum.srpint4app.mapper.TagMapper;
import ru.yandex.practicum.srpint4app.model.DbPost;
import ru.yandex.practicum.srpint4app.repository.PostRepository;
import ru.yandex.practicum.srpint4app.repository.TagRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final String UPLOAD_DIR = "$TOMCAT_HOME/webapps/static/";

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;
    private final TagMapper tagMapper;

    public Slice<PostPreviewDto> getAllPosts(Pageable pageable) {
        var dbPosts = postRepository.findAll(pageable);

        var dtoList = dbPosts.getContent()
            .stream()
            .map(postMapper::dbToDtoPreview)
            .toList();

        return new SliceImpl<>(dtoList, dbPosts.getPageable(), dbPosts.hasNext());
    }

    public PostDto getPostById(Integer id) {
        return postRepository.findById(id)
            .map(postMapper::dbToDto)
            .orElse(null);
    }

    public void createOrUpdatePost(PostDto post, MultipartFile file) {
        DbPost dbPost;
        var fileUrl = savePicture(file);
        post.setPicture(fileUrl);

        if (post.getId() != null) {
          dbPost = postRepository.findById(post.getId()).orElseThrow();
          updatePost(dbPost, post);
        }
        else {
          dbPost = postMapper.dtoToDb(post);
          setTags(dbPost, post);
        }

        postRepository.save(dbPost);
    }

    public void deletePost(Integer id) {
    postRepository.deleteById(id);
  }

    public void likePost(Integer id) {
        var dbPost = postRepository.findById(id).orElseThrow();
        dbPost.setLikesCount(dbPost.getLikesCount() + 1);
    }

    private void updatePost(DbPost dbPost, PostDto postDto) {
        tagRepository.deleteAllByPostId(dbPost.getId());
        dbPost.setTitle(postDto.getTitle());
        dbPost.setBody(postDto.getBody());
        dbPost.setPicture(postDto.getPicture());
        setTags(dbPost, postDto);
    }

    private void setTags(DbPost dbPost, PostDto postDto) {
        var dbTags = postDto.getTags()
            .stream()
            .map(tagMapper::dtoToDb)
            .toList();
        dbPost.setTags(dbTags);
    }

    private String savePicture(MultipartFile picture) {
        if (picture.isEmpty()) {
            return null;
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String pictureName = UUID.randomUUID() + "_" + picture.getOriginalFilename();

            Path filePath = uploadPath.resolve(pictureName);
            Files.copy(picture.getInputStream(), filePath);

            return "/static/" + pictureName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save picture", e);
        }
    }
}
