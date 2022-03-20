package com.example.demo.repository;

import com.example.demo.domain.Question;
import com.example.demo.repository.search.QuestionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {

}
