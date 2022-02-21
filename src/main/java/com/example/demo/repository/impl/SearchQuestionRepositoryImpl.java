package com.example.demo.repository.impl;

import static com.example.demo.domain.QProduct.product;
import static com.example.demo.domain.QQuestion.question;
import static com.example.demo.domain.QUser.user;
import static com.example.demo.domain.QComment.comment;

import com.example.demo.domain.Question;
import com.example.demo.dto.QQuestionDto_show;
import com.example.demo.dto.QQuestionDto_showDetail;
import com.example.demo.dto.QuestionDto;
import com.example.demo.repository.search.SearchQuestionRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class SearchQuestionRepositoryImpl implements SearchQuestionRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SearchQuestionRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<QuestionDto.show> findQuestionWithProductInfo(String username, Pageable pageable) {
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
                        .innerJoin(question.user, user).fetchJoin()
                        .innerJoin(question.product, product).fetchJoin()
                        .where(usernameEquals(username))
                        .orderBy(question.createDate.desc())
                        .fetch();

        JPAQuery<Question> questionCnt =
                jpaQueryFactory.selectFrom(question)
                        .innerJoin(question.user, user).fetchJoin()
                        .where(usernameEquals(username));

        return PageableExecutionUtils.getPage(questions, pageable, questionCnt::fetchCount);
    }

    @Override
    public QuestionDto.showDetail findQuestionAndCommentWithProductInfo(String username, Long questionId, Pageable pageable) {
        return jpaQueryFactory.select(
                                new QQuestionDto_showDetail(
                                        question.content,
                                        question.createDate,
                                        question.modifiedDate,
                                        product.imageUrl,
                                        product.title,
                                        comment.content,
                                        comment.createDate
                                )
                        )
                        .from(question)
                        .innerJoin(question.user, user).fetchJoin()
                        .innerJoin(question.product, product).fetchJoin()
                        .innerJoin(question.comments, comment).fetchJoin()
                        .where(usernameEquals(username), questionIdEquals(questionId))
                        .orderBy(question.createDate.desc())
                        .fetchOne();
    }

    public BooleanExpression usernameEquals(String username){
        return StringUtils.isEmpty(username) ? null : user.username.eq(username);
    }

    public BooleanExpression questionIdEquals(Long questionId){
        return question.id.eq(questionId);
    }
}
