package com.example.docqa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String filePath;

    @Column(columnDefinition = "TEXT")
    private String extractedText;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timestamp> timestamps = new ArrayList<>();

    // Constructors
    public Document() {
    }

    public Document(Long id, String filename, String fileType, String filePath,
                    String extractedText, String summary, LocalDateTime uploadedAt,
                    List<Timestamp> timestamps) {
        this.id = id;
        this.filename = filename;
        this.fileType = fileType;
        this.filePath = filePath;
        this.extractedText = extractedText;
        this.summary = summary;
        this.uploadedAt = uploadedAt;
        this.timestamps = timestamps != null ? timestamps : new ArrayList<>();
    }

    // Builder pattern
    public static DocumentBuilder builder() {
        return new DocumentBuilder();
    }

    public static class DocumentBuilder {
        private Long id;
        private String filename;
        private String fileType;
        private String filePath;
        private String extractedText;
        private String summary;
        private LocalDateTime uploadedAt;
        private List<Timestamp> timestamps = new ArrayList<>();

        public DocumentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DocumentBuilder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public DocumentBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public DocumentBuilder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public DocumentBuilder extractedText(String extractedText) {
            this.extractedText = extractedText;
            return this;
        }

        public DocumentBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public DocumentBuilder uploadedAt(LocalDateTime uploadedAt) {
            this.uploadedAt = uploadedAt;
            return this;
        }

        public DocumentBuilder timestamps(List<Timestamp> timestamps) {
            this.timestamps = timestamps;
            return this;
        }

        public Document build() {
            return new Document(id, filename, fileType, filePath, extractedText,
                    summary, uploadedAt, timestamps);
        }
    }

    // Getters and Setters
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public List<Timestamp> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<Timestamp> timestamps) {
        this.timestamps = timestamps;
    }
}