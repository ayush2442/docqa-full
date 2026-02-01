package com.example.docqa.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.docqa.dto.ChatResponse;
import com.example.docqa.dto.DocumentResponse;
import com.example.docqa.entity.Document;
import com.example.docqa.entity.Timestamp;
import com.example.docqa.repository.DocumentRepository;
import com.example.docqa.repository.TimestampRepository;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final TimestampRepository timestampRepository;
    private final FileStorageService fileStorageService;
    private final PdfExtractionService pdfExtractionService;
    private final TranscriptionService transcriptionService;
    private final GeminiService geminiService;

    public DocumentService(DocumentRepository documentRepository,
                           TimestampRepository timestampRepository,
                           FileStorageService fileStorageService,
                           PdfExtractionService pdfExtractionService,
                           TranscriptionService transcriptionService,
                           GeminiService geminiService) {
        this.documentRepository = documentRepository;
        this.timestampRepository = timestampRepository;
        this.fileStorageService = fileStorageService;
        this.pdfExtractionService = pdfExtractionService;
        this.transcriptionService = transcriptionService;
        this.geminiService = geminiService;
    }

    @Transactional
    public DocumentResponse uploadDocument(MultipartFile file) throws IOException {
        String filePath = fileStorageService.storeFile(file);
        String fileType = determineFileType(file.getOriginalFilename());
        String extractedText = "";

        Document document = Document.builder()
                .filename(file.getOriginalFilename())
                .fileType(fileType)
                .filePath(filePath)
                .uploadedAt(LocalDateTime.now())
                .build();

        if ("PDF".equals(fileType)) {
            extractedText = pdfExtractionService.extractText(filePath);
            document.setExtractedText(extractedText);
        } else if ("AUDIO".equals(fileType) || "VIDEO".equals(fileType)) {
            TranscriptionService.TranscriptionResult result = transcriptionService.transcribe(filePath);
            extractedText = result.getText();
            document.setExtractedText(extractedText);

            List<Timestamp> timestamps = transcriptionService.createTimestamps(result);
            Document finalDocument = document;
            timestamps.forEach(ts -> ts.setDocument(finalDocument));
            document.setTimestamps(timestamps);
        }

        if (!extractedText.isEmpty()) {
            String summary = geminiService.generateSummary(extractedText);
            document.setSummary(summary);
        }

        document = documentRepository.save(document);

        return mapToResponse(document);
    }

    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DocumentResponse getDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return mapToResponse(document);
    }

    public ChatResponse chat(Long documentId, String question) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        String context = document.getExtractedText();
        String answer = geminiService.answerQuestion(context, question);

        String[] words = question.toLowerCase().split("\\s+");
        List<Timestamp> matchedTimestamps = timestampRepository.findByDocumentId(documentId).stream()
                .filter(ts -> {
                    String text = ts.getText().toLowerCase();
                    for (String word : words) {
                        if (word.length() > 3 && text.contains(word)) {
                            return true;
                        }
                    }
                    return false;
                })
                .limit(5)
                .collect(Collectors.toList());

        List<ChatResponse.TimestampMatch> timestampMatches = matchedTimestamps.stream()
                .map(ts -> ChatResponse.TimestampMatch.builder()
                        .timeInSeconds(ts.getTimeInSeconds())
                        .text(ts.getText())
                        .build())
                .collect(Collectors.toList());

        return ChatResponse.builder()
                .answer(answer)
                .timestamps(timestampMatches)
                .build();
    }

    private String determineFileType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "pdf" -> "PDF";
            case "mp3", "wav", "m4a" -> "AUDIO";
            case "mp4", "avi", "mov" -> "VIDEO";
            default -> "UNKNOWN";
        };
    }

    private DocumentResponse mapToResponse(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .filename(document.getFilename())
                .fileType(document.getFileType())
                .extractedText(document.getExtractedText())
                .summary(document.getSummary())
                .uploadedAt(document.getUploadedAt())
                .build();
    }
}