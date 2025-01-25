package com.quiz.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CheckAnswersResponse {

    private List<AnswerResult> results;

    @Data
    @NoArgsConstructor
    public static class AnswerResult {
        private UUID id;
        private int selectedAnswerIndex;
        private boolean isCorrect;
        private int correctAnswerIndex;

        public AnswerResult(UUID id, int selectedAnswerIndex, boolean isCorrect, int correctAnswerIndex) {
            this.id = id;
            this.selectedAnswerIndex = selectedAnswerIndex;
            this.isCorrect = isCorrect;
            this.correctAnswerIndex = correctAnswerIndex;
        }
    }
}
