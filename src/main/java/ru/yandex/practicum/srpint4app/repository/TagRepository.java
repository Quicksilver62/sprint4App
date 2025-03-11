package ru.yandex.practicum.srpint4app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.srpint4app.model.DbTag;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<DbTag, Integer> {

    void deleteAllByPostId(int tagId);

    List<DbTag> findAllByPostId(int postId);
}
