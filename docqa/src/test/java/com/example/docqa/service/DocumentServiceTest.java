package com.example.docqa.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.example.docqa.dto.ChatResponse;
import com.example.docqa.dto.DocumentResponse;
import com.example.docqa.entity.Document;
import com.example.docqa.entity.Timestamp;
import com.example.docqa.repository.DocumentRepository;
import com.example.docqa.repository.TimestampRepository;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {
    
    @Mock
    private DocumentRepository documentRepository;
    
    @Mock
    private TimestampRepository timestampRepository;
    
    @Mock
    private FileStorageService fileStorageService;
    
    @Mock
    private PdfExtractionService pdfExtractionService;
    
    @Mock
    private TranscriptionService transcriptionService;
    
    @Mock
    private GeminiService geminiService;
    
    @InjectMocks
    private DocumentService documentService;
    
    @Test
    void testUploadPdfDocument() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());
        
        when(fileStorageService.storeFile(any())).thenReturn("/path/to/file.pdf");
        when(pdfExtractionService.extractText(anyString())).thenReturn("Extracted text");
        when(geminiService.generateSummary(anyString())).thenReturn("Summary");
        
        Document savedDoc = Document.builder()
                .id(1L)
                .filename("test.pdf")
                .fileType("PDF")
                .filePath("/path/to/file.pdf")
                .extractedText("Extracted text")
                .summary("Summary")
                .uploadedAt(LocalDateTime.now())
                .build();
        
        when(documentRepository.save(any(Document.class))).thenReturn(savedDoc);
        
        DocumentResponse response = documentService.uploadDocument(file);
        
        assertNotNull(response);
        assertEquals("test.pdf", response.getFilename());
        assertEquals("PDF", response.getFileType());
        assertEquals("Extracted text", response.getExtractedText());
        assertEquals("Summary", response.getSummary());
        
        verify(pdfExtractionService).extractText(anyString());
        verify(geminiService).generateSummary(anyString());
        verify(documentRepository).save(any(Document.class));
    }
    
    @Test
    void testGetAllDocuments() {
        List<Document> documents = Arrays.asList(
                Document.builder().id(1L).filename("doc1.pdf").fileType("PDF").filePath("/path1").uploadedAt(LocalDateTime.now()).build(),
                Document.builder().id(2L).filename("doc2.pdf").fileType("PDF").filePath("/path2").uploadedAt(LocalDateTime.now()).build()
        );
        
        when(documentRepository.findAll()).thenReturn(documents);
        
        List<DocumentResponse> responses = documentService.getAllDocuments();
        
        assertEquals(2, responses.size());
        verify(documentRepository).findAll();
    }
    
    @Test
    void testGetDocument() {
        Document document = Document.builder()
                .id(1L)
                .filename("test.pdf")
                .fileType("PDF")
                .filePath("/path")
                .extractedText("text")
                .uploadedAt(LocalDateTime.now())
                .build();
        
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        
        DocumentResponse response = documentService.getDocument(1L);
        
        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(documentRepository).findById(1L);
    }
    
    @Test
    void testChat() {
        Document document = Document.builder()
                .id(1L)
                .extractedText("This is the document text about artificial intelligence")
                .build();
        
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(geminiService.answerQuestion(anyString(), anyString())).thenReturn("Artificial intelligence is mentioned in the text");
        
        List<Timestamp> timestamps = Arrays.asList(
                Timestamp.builder().timeInSeconds(10.0).text("Artificial intelligence is important").build()
        );
        when(timestampRepository.findByDocumentId(1L)).thenReturn(timestamps);
        
        ChatResponse response = documentService.chat(1L, "What is artificial intelligence?");
        
        assertNotNull(response);
        assertEquals("Artificial intelligence is mentioned in the text", response.getAnswer());
        assertNotNull(response.getTimestamps());
        assertEquals(1, response.getTimestamps().size());
        
        verify(geminiService).answerQuestion(anyString(), anyString());
    }
}