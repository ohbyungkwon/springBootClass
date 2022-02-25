package com.example.demo.repository.search;

import com.example.demo.domain.enums.Category;
import com.example.demo.dto.CommentDto;
import com.example.demo.dto.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchQuestionRepository {
    Page<QuestionDto.show> findMyQuestionsWithProduct(Long userId, Pageable pageable);

    List<CommentDto.show> findCommentsInMyQuestion(Long userId, Long questionId);

    QuestionDto.showDetail findMyQuestionDetail(Long userId, Long questionId);

    List<QuestionDto.showSimple> findQuestionsInProduct(Long productId);
}
