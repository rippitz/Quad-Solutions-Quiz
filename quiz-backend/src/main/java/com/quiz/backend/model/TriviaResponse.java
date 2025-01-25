package com.quiz.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TriviaResponse{
    private String token;
    private int responseCode;
    private List<TriviaQuestion> results;
}
