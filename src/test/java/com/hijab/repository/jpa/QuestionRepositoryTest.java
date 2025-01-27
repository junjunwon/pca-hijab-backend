package com.hijab.repository.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "/sql/question-test-data.sql")
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void 인터뷰_질문_목록을_가져온다() {
        //when
        var result = questionRepository.findAll();

        //then
        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    void 인터뷰_질문_데이터가_없으면_Optional_Empty를_반환한다() {
        //given

        //when
        var result = questionRepository.findById(9999L);

        //then
        assertThat(result.isEmpty()).isTrue();
    }
}