package ru.yandex.practicum.srpint4app.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.yandex.practicum.srpint4app.model.DbPost;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        // Очистка базы данных
        postRepository.deleteAll();
    }

    @Test
    public void testSavePost() {
        DbPost post = new DbPost();
        post.setTitle("Test Post");
        post.setBody("This is a test post.");
        post.setCreatedAt(LocalDateTime.now());

        DbPost savedPost = postRepository.save(post);

        assertNotNull(savedPost.getId());
        assertEquals("Test Post", savedPost.getTitle());
    }

    @Test
    public void testFindPostById() {
        DbPost post = new DbPost();
        post.setTitle("Test Post");
        post.setBody("This is a test post.");
        post.setCreatedAt(LocalDateTime.now());
        DbPost savedPost = postRepository.save(post);

        Optional<DbPost> foundPost = postRepository.findById(savedPost.getId());

        assertTrue(foundPost.isPresent());
        assertEquals("Test Post", foundPost.get().getTitle());
    }

    @Test
    public void testDeletePost() {
        DbPost post = new DbPost();
        post.setTitle("Test Post");
        post.setBody("This is a test post.");
        post.setCreatedAt(LocalDateTime.now());
        DbPost savedPost = postRepository.save(post);

        postRepository.deleteById(savedPost.getId());

        Optional<DbPost> deletedPost = postRepository.findById(savedPost.getId());
        assertFalse(deletedPost.isPresent());
    }

    @Test
    public void testFindAllPostsWithPagination() {
        for (int i = 1; i <= 10; i++) {
            DbPost post = new DbPost();
            post.setTitle("Post " + i);
            post.setBody("This is post " + i);
            post.setCreatedAt(LocalDateTime.now());
            postRepository.save(post);
        }

        Page<DbPost> postsPage = postRepository.findAll(PageRequest.of(0, 5));

        assertEquals(10, postsPage.getTotalElements());
        assertEquals(5, postsPage.getContent().size());
    }
}
