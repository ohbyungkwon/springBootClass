package com.example.demo.controller;

import com.example.demo.domain.Question;
import com.example.demo.dto.CommentDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question")
    public ResponseEntity<ResponseComDto> createQuestion(QuestionDto.create questionDto, Principal principal) {
        String username = principal.getName();
        QuestionDto.create createDto = questionService.createQuestion(questionDto, username);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("질문이 등록되었습니다.")
                        .resultObj(createDto)
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<ResponseComDto> createComment(CommentDto.create commentDto, Principal principal) {
        String username = principal.getName();
        CommentDto.create createDto = questionService.createComment(commentDto, username);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("답변이 등록되었습니다.")
                        .resultObj(createDto)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity<ResponseComDto> searchQuestions(Pageable pageable, Principal principal) {
        String username = principal.getName();
        Page<QuestionDto.show> questions = questionService.showQuestionList(username, pageable);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg(null)
                        .resultObj(questions)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/questions/{questionId}")
    public ResponseEntity<ResponseComDto> searchQuestions(@PathVariable Long questionId,
                                                          Pageable pageable, Principal principal) {
        String username = principal.getName();
        QuestionDto.showDetail questionDetail = questionService.showQuestionDetail(username, questionId, pageable);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg(null)
                        .resultObj(questionDetail)
                        .build(), HttpStatus.OK);
    }
}
