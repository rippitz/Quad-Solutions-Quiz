package com.quiz.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
public class CheckAnswersRequest {
    private List<AnsweredQuestion> questions;

    @Data
    @NoArgsConstructor
    public static class AnsweredQuestion {
        private UUID id;
        private int selectedAnswerIndex;
    }
}
