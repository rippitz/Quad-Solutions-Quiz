package com.quiz.backend.service;

import com.quiz.backend.model.QuizQuestion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizDataStore {

    private final List<QuizQuestion> quizQuestions = new ArrayList<>();

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> questions) {
        quizQuestions.clear();
        quizQuestions.addAll(questions);
    }
}
