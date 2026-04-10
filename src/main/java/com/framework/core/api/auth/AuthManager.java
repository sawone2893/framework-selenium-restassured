package com.framework.core.api.auth;

import java.time.Instant;

import com.framework.core.api.builder.RequestBuilderFactory;
import com.framework.core.api.model.ApiResponse;
import com.framework.utils.EnvConfig;

public class AuthManager {
	// Thread-safe token storage
    private static ThreadLocal<String> token = new ThreadLocal<>();
    private static ThreadLocal<Instant> tokenExpiry = new ThreadLocal<>();

    // ========================
    // PUBLIC METHOD
    // ========================

    public static String getToken() {

        // If token is missing or expired → regenerate
        if (token.get() == null || isTokenExpired()) {
            generateToken();
        }

        return token.get();
    }

    // ========================
    // TOKEN GENERATION
    // ========================

    private static void generateToken() {

        String username = EnvConfig.get("API.AUTH.USERNAME");
        String password = EnvConfig.get("API.AUTH.PASSWORD");

        String authEndpoint = EnvConfig.get("API.AUTH.ENDPOINT");

        // Build request body
        String requestBody = String.format("""
                {
                  "username": "%s",
                  "password": "%s"
                }
                """, username, password);

        // Call API using your framework itself
        ApiResponse response = RequestBuilderFactory.request()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(authEndpoint);

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Auth failed: " + response.getBody());
        }

        // Extract token
        String newToken = response.getJsonValue("token");

        // Store token
        token.set(newToken);

        // Set expiry (example: 10 minutes)
        tokenExpiry.set(Instant.now().plusSeconds(600));

        System.out.println("Generated new token for Thread: "
                + Thread.currentThread().getName());
    }

    // ========================
    // TOKEN EXPIRY CHECK
    // ========================

    private static boolean isTokenExpired() {
        return tokenExpiry.get() == null ||
               Instant.now().isAfter(tokenExpiry.get());
    }

    // ========================
    // CLEAR (IMPORTANT FOR PARALLEL)
    // ========================

    public static void clear() {
        token.remove();
        tokenExpiry.remove();
    }
}
