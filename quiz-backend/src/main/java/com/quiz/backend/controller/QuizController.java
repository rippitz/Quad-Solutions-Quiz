package com.quiz.backend.controller;

import com.quiz.backend.model.CheckAnswersRequest;
import com.quiz.backend.model.CheckAnswersResponse;
import com.quiz.backend.model.QuizQuestion;
import com.quiz.backend.service.CheckAnswerService;
import com.quiz.backend.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;
    private final CheckAnswerService checkAnswerService;

    public QuizController(QuizService quizService, CheckAnswerService checkAnswerService) {
        this.quizService = quizService;
        this.checkAnswerService = checkAnswerService;
    }

    @GetMapping("/questions")
    public List<QuizQuestion> getQuestions() {
        return quizService.fetchAndPrepareQuestions();
    }

    @PostMapping("/check-answers")
    public ResponseEntity<CheckAnswersResponse> checkAnswers(@RequestBody CheckAnswersRequest request){
        CheckAnswersResponse response = checkAnswerService.checkAnswers(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
