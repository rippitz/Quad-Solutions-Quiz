package com.quiz.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class QuizQuestion {
    private UUID id;
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private List<String> answers;
    @JsonIgnore
    private String correctAnswer;
    @JsonIgnore
    private int correctAnswerIndex;
}
