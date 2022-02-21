package com.example.demo.repository.search;

import com.example.demo.domain.enums.Category;
import com.example.demo.dto.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchQuestionRepository {
    Page<QuestionDto.show> findQuestionWithProductInfo(String username, Pageable pageable);

    QuestionDto.showDetail findQuestionAndCommentWithProductInfo(String username, Long questionId, Pageable pageable);
}
