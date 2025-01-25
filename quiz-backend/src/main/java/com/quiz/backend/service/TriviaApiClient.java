package com.quiz.backend.service;

import com.quiz.backend.model.TriviaQuestion;
import com.quiz.backend.model.TriviaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class TriviaApiClient {

    private static final String BASE_URL = "https://opentdb.com";
    private static final String QUESTIONS_API = "/api.php";
    private static final String TOKEN_API = "/api_token.php";
    private static final int MAX_RETRIES = 3;
    private static final int SLEEP_DURATION_IN_MS = 5000;

    private final RestTemplate restTemplate = new RestTemplate();
    private String sessionToken;

    public TriviaApiClient() {
        this.sessionToken = getSessionToken();
    }

    private String getSessionToken() {
        String url = BASE_URL + TOKEN_API + "?command=request";
        TriviaResponse tokenResponse = restTemplate.getForObject(url, TriviaResponse.class);
        if (tokenResponse != null && tokenResponse.getToken() != null) {
            return tokenResponse.getToken();
        }
        throw new RuntimeException("Failed to retrieve session token");
    }

    public List<TriviaQuestion> getQuestions() {
        if (sessionToken == null) {
            sessionToken = getSessionToken();
        }

        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + QUESTIONS_API)
                .queryParam("amount", 10)
                .queryParam("token", sessionToken)
                .toUriString();

        TriviaResponse response = null;

        // Retry logic
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            response = restTemplate.getForObject(url, TriviaResponse.class);
            if (response != null) {
                switch (response.getResponseCode()) {
                    case 0: // Success
                        return response.getResults();
                    case 1: // No Results
                        throw new RuntimeException("No results returned for the given query.");
                    case 3: // Token Not Found
                        throw new RuntimeException("Session token not found. Requesting a new token.");
                    case 4: // Token Empty (Exhausted all questions)
                        resetSessionToken();
                        break;
                    case 5: // Rate Limit Exceeded
                        try {
                            Thread.sleep(SLEEP_DURATION_IN_MS); // Sleep for 5 seconds before retrying
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        break;
                    default:
                        throw new RuntimeException("Unexpected response code: " + response.getResponseCode());
                }
            }
        }

        throw new RuntimeException(String.format("Failed to fetch trivia questions after %d attempts.", MAX_RETRIES));
    }

    public void resetSessionToken() {
        if (sessionToken != null) {
            String url = BASE_URL + TOKEN_API + "?command=reset&token=" + sessionToken;
            restTemplate.getForObject(url, String.class);
        }
        sessionToken = getSessionToken();
    }
}
