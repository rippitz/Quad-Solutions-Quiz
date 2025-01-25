package com.quiz.backend.dao;

import com.quiz.backend.model.QuizQuestion;

import java.util.List;

public interface iQuizDataStore {

    List<QuizQuestion> getQuizQuestions();

    void setQuizQuestions(List<QuizQuestion> questions);
}
