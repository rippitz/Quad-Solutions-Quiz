package com.quiz.backend.service;

import com.quiz.backend.model.QuizQuestion;
import com.quiz.backend.model.TriviaQuestion;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final TriviaApiClient triviaApiClient;
    private final QuizDataStore quizDataStore;

    public QuizService(TriviaApiClient triviaApiClient, QuizDataStore quizDataStore) {
        this.triviaApiClient = triviaApiClient;
        this.quizDataStore = quizDataStore;
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

        quizDataStore.setQuizQuestions(preparedQuestions);
        return preparedQuestions;
    }

    private String unescapeHtml(String input) {
        return input != null ? StringEscapeUtils.unescapeHtml4(input) : null;
    }
}
