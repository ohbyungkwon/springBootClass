package com.example.demo.service;

import com.example.demo.domain.Comment;
import com.example.demo.domain.Product;
import com.example.demo.domain.Question;
import com.example.demo.domain.User;
import com.example.demo.dto.CommentDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.exception.BadClientException;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, CommentRepository commentRepository,
                           UserRepository userRepository, ProductRepository productRepository,
                           ProductOrderRepository productOrderRepository) {
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productOrderRepository = productOrderRepository;
    }

    @Transactional
    public QuestionDto.create createQuestion(QuestionDto.create questionDto, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadClientException("사용자 정보가 없습니다."));

        Long productId = questionDto.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadClientException("상품이 존재하지 않습니다."));

        Boolean isBuy = productOrderRepository.existsByProductIdAndUserId(productId, user.getId());

        Question question = Question.builder()
                .content(questionDto.getContent())
                .isBuy(isBuy)
                .deleted(false)
                .user(user)
                .product(product)
                .build();

        questionRepository.save(question);

        return questionDto;
    }

    @Transactional
    public CommentDto.create createComment(CommentDto.create commentDto, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadClientException("사용자 정보가 없습니다."));

        Long questionId = commentDto.getQuestionId();
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BadClientException("QnA 질문이 없습니다."));

        Comment comment = Comment.commentBuilder(commentDto, question, user);
        comment.joinQuestion(question);

        commentRepository.save(comment);
        return commentDto;
    }
}
