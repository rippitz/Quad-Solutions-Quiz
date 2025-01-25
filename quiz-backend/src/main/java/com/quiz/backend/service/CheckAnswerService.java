package com.quiz.backend.service;

import com.quiz.backend.model.CheckAnswersRequest;
import com.quiz.backend.model.CheckAnswersResponse;
import com.quiz.backend.model.QuizQuestion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CheckAnswerService {

    private final QuizDataStore quizDataStore;

    public CheckAnswerService(QuizDataStore quizDataStore) {
        this.quizDataStore = quizDataStore;
    }

    public CheckAnswersResponse checkAnswers(CheckAnswersRequest request) {
        List<QuizQuestion> quizQuestions = quizDataStore.getQuizQuestions();

        List<CheckAnswersResponse.AnswerResult> results = request.getQuestions().stream()
                .map(answeredQuestion -> {
                    QuizQuestion quizQuestion = quizQuestions.stream()
                            .filter(q -> q.getId().equals(answeredQuestion.getId()))
                            .findFirst()
                            .orElse(null);

                    if (quizQuestion != null) {
                        boolean isCorrect = answeredQuestion.getSelectedAnswerIndex() == quizQuestion.getCorrectAnswerIndex();

                        return new CheckAnswersResponse.AnswerResult(
                                answeredQuestion.getId(),
                                answeredQuestion.getSelectedAnswerIndex(),
                                isCorrect,
                                quizQuestion.getCorrectAnswerIndex()
                        );
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        CheckAnswersResponse response = new CheckAnswersResponse();
        response.setResults(results);
        return response;
    }
}
