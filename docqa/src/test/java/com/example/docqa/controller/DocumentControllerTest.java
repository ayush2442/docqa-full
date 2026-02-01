package com.example.docqa.controller;

import com.example.docqa.dto.ChatRequest;
import com.example.docqa.dto.ChatResponse;
import com.example.docqa.dto.DocumentResponse;
import com.example.docqa.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DocumentController.class)
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DocumentService documentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUploadDocument() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes());

        DocumentResponse response = DocumentResponse.builder()
                .id(1L)
                .filename("test.pdf")
                .fileType("PDF")
                .uploadedAt(LocalDateTime.now())
                .build();

        when(documentService.uploadDocument(any())).thenReturn(response);

        mockMvc.perform(multipart("/api/documents/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.filename").value("test.pdf"));
    }

    @Test
    void testGetAllDocuments() throws Exception {
        DocumentResponse doc = DocumentResponse.builder()
                .id(1L)
                .filename("test.pdf")
                .fileType("PDF")
                .uploadedAt(LocalDateTime.now())
                .build();

        when(documentService.getAllDocuments()).thenReturn(Arrays.asList(doc));

        mockMvc.perform(get("/api/documents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testChat() throws Exception {
        ChatRequest request = new ChatRequest(1L, "What is AI?");
        ChatResponse response = ChatResponse.builder()
                .answer("AI is artificial intelligence")
                .timestamps(Collections.emptyList())
                .build();

        when(documentService.chat(1L, "What is AI?")).thenReturn(response);

        mockMvc.perform(post("/api/documents/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value("AI is artificial intelligence"));
    }
}