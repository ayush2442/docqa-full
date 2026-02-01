package com.example.docqa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
public class GeminiService {

    private final Client client;

    public GeminiService(@Value("${gemini.api-key}") String apiKey) {
        this.client = Client.builder().apiKey(apiKey).build();
    }

    public String generateSummary(String text) {
        try {
            String truncatedText = text.length() > 30000 ? text.substring(0, 30000) + "..." : text;
            String prompt = "Please provide a concise summary of the following text:\n\n" + truncatedText;

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3-flash-preview",
                    prompt,
                    null
            );

            return response.text();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate summary: " + e.getMessage(), e);
        }
    }

    public String answerQuestion(String context, String question) {
        try {
            String truncatedContext = context.length() > 30000 ? context.substring(0, 30000) + "..." : context;
            String prompt = "Based on the following context, please answer the question. " +
                    "Only use information from the context to answer.\n\n" +
                    "Context:\n" + truncatedContext + "\n\n" +
                    "Question: " + question;

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3-flash-preview",
                    prompt,
                    null
            );

            return response.text();
        } catch (Exception e) {
            throw new RuntimeException("Failed to answer question: " + e.getMessage(), e);
        }
    }
}