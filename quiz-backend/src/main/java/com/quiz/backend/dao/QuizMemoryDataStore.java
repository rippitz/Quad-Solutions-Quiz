package com.quiz.backend.dao;

import com.quiz.backend.model.QuizQuestion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizMemoryDataStore implements
 iQuizDataStore {

    private final List<QuizQuestion> quizQuestions = new ArrayList<>();

    @Override
    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    @Override
    public void setQuizQuestions(List<QuizQuestion> questions) {
        quizQuestions.clear();
        quizQuestions.addAll(questions);
    }
}
