package ru.yandex.practicum.srpint4app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.srpint4app.model.DbPost;

@Repository
public interface PostRepository extends JpaRepository<DbPost, Integer> {

    Page<DbPost> findAll(Pageable pageable);
}
