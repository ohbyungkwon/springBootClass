package com.example.demo.repository.impl;

import static com.example.demo.domain.QProduct.product;
import static com.example.demo.domain.QQuestion.question;
import static com.example.demo.domain.QUser.user;
import static com.example.demo.domain.QComment.comment;

import com.example.demo.domain.Comment;
import com.example.demo.domain.Question;
import com.example.demo.dto.*;
import com.example.demo.repository.search.SearchQuestionRepository;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SearchQuestionRepositoryImpl implements SearchQuestionRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SearchQuestionRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<QuestionDto.show> findMyQuestionsWithProduct(Long userId, Pageable pageable) {
        List<QuestionDto.show> questions =
                jpaQueryFactory.select(
                            new QQuestionDto_show(
                                    question.content,
                                    question.createDate,
                                    question.modifiedDate,
                                    product.imageUrl,
                                    product.title
                            )
                        )
                        .from(question)
                        .innerJoin(question.user, user)
                        .innerJoin(question.product, product).fetchJoin()
                        .where(userIdEquals(userId))
                        .orderBy(question.createDate.desc())
                        .fetch();

        JPAQuery<Question> questionCnt =
                jpaQueryFactory.selectFrom(question)
                        .innerJoin(question.user, user)
                        .where(userIdEquals(userId));

        return PageableExecutionUtils.getPage(questions, pageable, questionCnt::fetchCount);
    }

    @Override
    public List<CommentDto.show> findCommentsInMyQuestion(Long userId, Long questionId) {
        return jpaQueryFactory
                .select(
                        new QCommentDto_show(
                                comment.content,
                                comment.createDate,
                                comment.user.username
                        )
                )
                .from(comment)
                .innerJoin(comment.question, question)
                .innerJoin(comment.user, user).fetchJoin()
                .where(questionIdEquals(questionId))
                .orderBy(comment.createDate.desc())
                .fetch();
    }

    @Override
    public QuestionDto.showDetail findMyQuestionDetail(Long userId, Long questionId) {
        Map<Question, List<Comment>> transform = jpaQueryFactory
                .from(question)
                .innerJoin(question.user, user).fetchJoin()
                .innerJoin(question.product, product).fetchJoin()
                .innerJoin(question.comments, comment).fetchJoin()
                .where(userIdEquals(userId), questionIdEquals(questionId))
                .orderBy(question.createDate.desc())
                .transform(GroupBy.groupBy(question).as(GroupBy.list(comment)));

        return transform.entrySet().stream()
                .map( x -> new QuestionDto.showDetail(
                        x.getKey().getContent(),
                        x.getKey().getCreateDate(),
                        x.getKey().getModifiedDate(),
                        x.getKey().getProduct().getImageUrl(),
                        x.getKey().getProduct().getTitle(),
                        x.getValue().stream()
                                .map(y -> new CommentDto.show(
                                        y.getContent(),
                                        y.getCreateDate(),
                                        y.getUser().getUsername()
                                    )).collect(Collectors.toList())
                )).collect(Collectors.toList()).get(0);
    }

    @Override
    public List<QuestionDto.showSimple> findQuestionsInProduct(Long productId) {
        return jpaQueryFactory.select(
                        new QQuestionDto_showSimple(
                                question.content,
                                question.createDate,
                                question.modifiedDate
                        )
                )
                .from(question)
                .innerJoin(question.product, product)
                .where(productIdEquals(productId))
                .fetch();
    }

    public BooleanExpression userIdEquals(Long userId){ return user.id.eq(userId); }

    public BooleanExpression questionIdEquals(Long questionId){
        return question.id.eq(questionId);
    }

    public BooleanExpression productIdEquals(Long productId){ return question.product.id.eq(productId); }
}
