package com.quiz.backend.service;

import com.quiz.backend.dao.QuizMemoryDataStore;
import com.quiz.backend.model.QuizQuestion;
import com.quiz.backend.model.TriviaQuestion;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class QuizService {

    private final TriviaApiClient triviaApiClient;
    private final QuizMemoryDataStore quizMemoryDataStore;

    public QuizService(TriviaApiClient triviaApiClient, QuizMemoryDataStore quizMemoryDataStore) {
        this.triviaApiClient = triviaApiClient;
        this.quizMemoryDataStore = quizMemoryDataStore;
    }

    public List<QuizQuestion> fetchAndPrepareQuestions() {
        List<TriviaQuestion> triviaQuestions = triviaApiClient.getQuestions();

        List<QuizQuestion> preparedQuestions = triviaQuestions.stream().map(trivia -> {
            QuizQuestion quiz = new QuizQuestion();
            quiz.setId(UUID.randomUUID());
            quiz.setCategory(trivia.getCategory());
            quiz.setType(trivia.getType());
            quiz.setDifficulty(trivia.getDifficulty());
            quiz.setQuestion(unescapeHtml(trivia.getQuestion()));
            quiz.setCorrectAnswer(trivia.getCorrectAnswer());

            List<String> answers = trivia.getIncorrectAnswers().stream()
                    .map(this::unescapeHtml)
                    .collect(Collectors.toList());
            answers.add(unescapeHtml(trivia.getCorrectAnswer()));

            if ("multiple".equals(trivia.getType())) {
                Collections.shuffle(answers);
            } else if ("boolean".equals(trivia.getType())) {
                answers = List.of("True", "False");
            }

            quiz.setAnswers(answers);
            int correctAnswerIndex = answers.indexOf(trivia.getCorrectAnswer());
            quiz.setCorrectAnswerIndex(correctAnswerIndex);
            return quiz;
        }).collect(Collectors.toList());

        quizMemoryDataStore.setQuizQuestions(preparedQuestions);
        return preparedQuestions;
    }

    private String unescapeHtml(String input) {
        return input != null ? StringEscapeUtils.unescapeHtml4(input) : null;
    }
}
