package com.example.docqa.dto;

import java.time.LocalDateTime;

public class DocumentResponse {
    private Long id;
    private String filename;
    private String fileType;
    private String extractedText;
    private String summary;
    private LocalDateTime uploadedAt;

    public DocumentResponse() {
    }

    public DocumentResponse(Long id, String filename, String fileType, String extractedText,
                            String summary, LocalDateTime uploadedAt) {
        this.id = id;
        this.filename = filename;
        this.fileType = fileType;
        this.extractedText = extractedText;
        this.summary = summary;
        this.uploadedAt = uploadedAt;
    }

    public static DocumentResponseBuilder builder() {
        return new DocumentResponseBuilder();
    }

    public static class DocumentResponseBuilder {
        private Long id;
        private String filename;
        private String fileType;
        private String extractedText;
        private String summary;
        private LocalDateTime uploadedAt;

        public DocumentResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DocumentResponseBuilder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public DocumentResponseBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public DocumentResponseBuilder extractedText(String extractedText) {
            this.extractedText = extractedText;
            return this;
        }

        public DocumentResponseBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public DocumentResponseBuilder uploadedAt(LocalDateTime uploadedAt) {
            this.uploadedAt = uploadedAt;
            return this;
        }

        public DocumentResponse build() {
            return new DocumentResponse(id, filename, fileType, extractedText, summary, uploadedAt);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}