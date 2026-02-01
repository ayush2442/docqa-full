package com.example.docqa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.docqa.entity.Timestamp;

@Service
public class SpeechToTextService {

    public TranscriptionResult transcribe(String filePath) {
        try {
            String transcription = "Audio/Video transcription requires Google Cloud credentials. " +
                    "For this MVP, please use PDF documents. " +
                    "To enable audio transcription, set up Google Cloud Speech-to-Text API credentials.";

            return new TranscriptionResult(transcription);
        } catch (Exception e) {
            throw new RuntimeException("Transcription failed: " + e.getMessage(), e);
        }
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