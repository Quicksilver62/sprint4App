package ru.yandex.practicum.srpint4app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.srpint4app.model.DbComment;

@Repository
public interface CommentRepository extends JpaRepository<DbComment, Integer> {

}
