package com.quiz.backend.service;

import com.quiz.backend.dao.QuizMemoryDataStore;
import com.quiz.backend.model.QuizQuestion;
import com.quiz.backend.model.TriviaQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    private TriviaApiClient triviaApiClient;
    private QuizMemoryDataStore quizMemoryDataStore;
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        triviaApiClient = Mockito.mock(TriviaApiClient.class);
        quizMemoryDataStore = Mockito.mock(QuizMemoryDataStore.class);


        quizService = new QuizService(triviaApiClient, quizMemoryDataStore);
    }

    @Test
    void testFetchAndPrepareQuestions_MultipleChoice() {

        TriviaQuestion triviaQuestion = new TriviaQuestion();
        triviaQuestion.setCategory("General Knowledge");
        triviaQuestion.setType("multiple");
        triviaQuestion.setDifficulty("medium");
        triviaQuestion.setQuestion("What is the capital of France?");
        triviaQuestion.setCorrectAnswer("Paris");
        triviaQuestion.setIncorrectAnswers(List.of("Berlin", "Madrid", "Rome"));


        when(triviaApiClient.getQuestions()).thenReturn(List.of(triviaQuestion));


        List<QuizQuestion> result = quizService.fetchAndPrepareQuestions();


        verify(triviaApiClient, times(1)).getQuestions();
        verify(quizMemoryDataStore, times(1)).setQuizQuestions(result);

        assertEquals(1, result.size());
        QuizQuestion preparedQuestion = result.get(0);

        assertEquals("General Knowledge", preparedQuestion.getCategory());
        assertEquals("multiple", preparedQuestion.getType());
        assertEquals("medium", preparedQuestion.getDifficulty());
        assertEquals("What is the capital of France?", preparedQuestion.getQuestion());
        assertEquals("Paris", preparedQuestion.getCorrectAnswer());
        assertEquals(4, preparedQuestion.getAnswers().size()); // 1 correct + 3 incorrect
        assertEquals(preparedQuestion.getCorrectAnswerIndex(),
                preparedQuestion.getAnswers().indexOf(preparedQuestion.getCorrectAnswer()));
    }

    @Test
    void testFetchAndPrepareQuestions_BooleanChoice() {
        TriviaQuestion triviaQuestion = new TriviaQuestion();
        triviaQuestion.setCategory("Science");
        triviaQuestion.setType("boolean");
        triviaQuestion.setDifficulty("easy");
        triviaQuestion.setQuestion("The Earth is flat.");
        triviaQuestion.setCorrectAnswer("False");
        triviaQuestion.setIncorrectAnswers(List.of("True"));

        when(triviaApiClient.getQuestions()).thenReturn(List.of(triviaQuestion));

        List<QuizQuestion> result = quizService.fetchAndPrepareQuestions();

        verify(triviaApiClient, times(1)).getQuestions();
        verify(quizMemoryDataStore, times(1)).setQuizQuestions(result);

        assertEquals(1, result.size());
        QuizQuestion preparedQuestion = result.get(0);

        assertEquals("Science", preparedQuestion.getCategory());
        assertEquals("boolean", preparedQuestion.getType());
        assertEquals("easy", preparedQuestion.getDifficulty());
        assertEquals("The Earth is flat.", preparedQuestion.getQuestion());
        assertEquals("False", preparedQuestion.getCorrectAnswer());
        assertEquals(List.of("True", "False"), preparedQuestion.getAnswers());
        assertEquals(1, preparedQuestion.getCorrectAnswerIndex()); // "False" should be at index 1
    }

    @Test
    void testFetchAndPrepareQuestions_EmptyTriviaQuestions() {

        when(triviaApiClient.getQuestions()).thenReturn(List.of());

        List<QuizQuestion> result = quizService.fetchAndPrepareQuestions();

        verify(triviaApiClient, times(1)).getQuestions();
        verify(quizMemoryDataStore, times(1)).setQuizQuestions(result);

        assertEquals(0, result.size());
    }
}