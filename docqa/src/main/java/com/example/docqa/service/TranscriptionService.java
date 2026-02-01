package com.example.docqa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.docqa.entity.Timestamp;

@Service
public class TranscriptionService {

    public TranscriptionResult transcribe(String filePath) {
        String transcription = "Audio/Video transcription is not available with the free Gemini API. " +
                "Please use the PDF upload feature for text-based documents.";

        return new TranscriptionResult(transcription);
    }

    public List<Timestamp> createTimestamps(TranscriptionResult result) {
        List<Timestamp> timestamps = new ArrayList<>();

        Timestamp timestamp = Timestamp.builder()
                .timeInSeconds(0.0)
                .text(result.getText())
                .build();
        timestamps.add(timestamp);

        return timestamps;
    }

    public static class TranscriptionResult {
        private final String text;

        public TranscriptionResult(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}