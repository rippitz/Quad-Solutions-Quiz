package com.quiz.backend.service;

import com.quiz.backend.dao.QuizMemoryDataStore;
import com.quiz.backend.model.CheckAnswersRequest;
import com.quiz.backend.model.CheckAnswersResponse;
import com.quiz.backend.model.QuizQuestion;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckAnswerService {

    private final QuizMemoryDataStore quizMemoryDataStore;

    public CheckAnswerService(QuizMemoryDataStore quizMemoryDataStore) {
        this.quizMemoryDataStore = quizMemoryDataStore;
    }

    public CheckAnswersResponse checkAnswers(CheckAnswersRequest request) {
        // Step 1: Create a HashMap for quick lookup
        List<QuizQuestion> quizQuestions = quizMemoryDataStore.getQuizQuestions();
        Map<UUID, QuizQuestion> questionMap = quizQuestions.stream()
                .collect(Collectors.toMap(QuizQuestion::getId, quizQuestion -> quizQuestion));



        List<CheckAnswersResponse.AnswerResult> results = request.getQuestions().stream()
                .map(answeredQuestion -> {
                    QuizQuestion quizQuestion = questionMap.get(answeredQuestion.getId());

                    if (quizQuestion == null) {
                        return null;
                    }

                    boolean isCorrect = answeredQuestion.getSelectedAnswerIndex() == quizQuestion.getCorrectAnswerIndex();
                    return new CheckAnswersResponse.AnswerResult(
                            answeredQuestion.getId(),
                            answeredQuestion.getSelectedAnswerIndex(),
                            isCorrect,
                            quizQuestion.getCorrectAnswerIndex()
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        CheckAnswersResponse response = new CheckAnswersResponse();
        response.setResults(results);
        return response;
    }
}
