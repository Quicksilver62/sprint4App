package ru.yandex.practicum.srpint4app.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.srpint4app.dto.CommentDto;
import ru.yandex.practicum.srpint4app.mapper.CommentMapper;
import ru.yandex.practicum.srpint4app.model.DbComment;
import ru.yandex.practicum.srpint4app.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public void createOrUpdateComment(CommentDto commentDto) {

        DbComment dbComment;
        if (commentDto.getId() != null) {
            dbComment = commentRepository.findById(commentDto.getId()).orElseThrow();
            dbComment.setText(commentDto.getText());
        }
        else {
            dbComment = commentMapper.dtoToDb(commentDto);
        }
        commentRepository.save(dbComment);
    }

    public void deleteComment(Integer id) {
    commentRepository.deleteById(id);
  }
}
