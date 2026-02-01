package com.example.docqa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "timestamps")
public class Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(nullable = false)
    private Double timeInSeconds;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    // Constructors
    public Timestamp() {
    }

    public Timestamp(Long id, Document document, Double timeInSeconds, String text) {
        this.id = id;
        this.document = document;
        this.timeInSeconds = timeInSeconds;
        this.text = text;
    }

    // Builder
    public static TimestampBuilder builder() {
        return new TimestampBuilder();
    }

    public static class TimestampBuilder {
        private Long id;
        private Document document;
        private Double timeInSeconds;
        private String text;

        public TimestampBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TimestampBuilder document(Document document) {
            this.document = document;
            return this;
        }

        public TimestampBuilder timeInSeconds(Double timeInSeconds) {
            this.timeInSeconds = timeInSeconds;
            return this;
        }

        public TimestampBuilder text(String text) {
            this.text = text;
            return this;
        }

        public Timestamp build() {
            return new Timestamp(id, document, timeInSeconds, text);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
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