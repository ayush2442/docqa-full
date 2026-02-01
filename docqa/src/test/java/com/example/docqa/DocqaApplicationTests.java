package com.example.docqa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"gemini.api-key=test-key",
		"file.upload-dir=./test-uploads"
})
class DocqaApplicationTests {

	@Test
	void contextLoads() {
	}

}