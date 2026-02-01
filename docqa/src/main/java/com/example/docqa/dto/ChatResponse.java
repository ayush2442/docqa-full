package com.example.docqa.dto;

import java.util.List;

public class ChatResponse {
    private String answer;
    private List<TimestampMatch> timestamps;

    public ChatResponse() {
    }

    public ChatResponse(String answer, List<TimestampMatch> timestamps) {
        this.answer = answer;
        this.timestamps = timestamps;
    }

    public static ChatResponseBuilder builder() {
        return new ChatResponseBuilder();
    }

    public static class ChatResponseBuilder {
        private String answer;
        private List<TimestampMatch> timestamps;

        public ChatResponseBuilder answer(String answer) {
            this.answer = answer;
            return this;
        }

        public ChatResponseBuilder timestamps(List<TimestampMatch> timestamps) {
            this.timestamps = timestamps;
            return this;
        }

        public ChatResponse build() {
            return new ChatResponse(answer, timestamps);
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<TimestampMatch> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<TimestampMatch> timestamps) {
        this.timestamps = timestamps;
    }

    public static class TimestampMatch {
        private Double timeInSeconds;
        private String text;

        public TimestampMatch() {
        }

        public TimestampMatch(Double timeInSeconds, String text) {
            this.timeInSeconds = timeInSeconds;
            this.text = text;
        }

        public static TimestampMatchBuilder builder() {
            return new TimestampMatchBuilder();
        }

        public static class TimestampMatchBuilder {
            private Double timeInSeconds;
            private String text;

            public TimestampMatchBuilder timeInSeconds(Double timeInSeconds) {
                this.timeInSeconds = timeInSeconds;
                return this;
            }

            public TimestampMatchBuilder text(String text) {
                this.text = text;
                return this;
            }

            public TimestampMatch build() {
                return new TimestampMatch(timeInSeconds, text);
            }
        }

        public Double getTimeInSeconds() {
            return timeInSeconds;
        }

        public void setTimeInSeconds(Double timeInSeconds) {
            this.timeInSeconds = timeInSeconds;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}