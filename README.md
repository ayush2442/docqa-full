# DocQA - AI-Powered Document & Multimedia Q&A Application

A Spring Boot + React application that allows users to upload PDF documents, extract text, generate summaries, and ask questions using Google Gemini AI.

## Features

- **PDF Upload & Text Extraction** - Upload PDFs and automatically extract text using Apache PDFBox
- **AI-Powered Summaries** - Generate concise summaries using Google Gemini AI
- **Intelligent Q&A** - Ask questions about your documents and get AI-generated answers
- **Audio/Video Support** - Upload multimedia files (transcription not available in free tier)
- **Keyword Search** - Find relevant timestamps in transcribed content
- **Timestamp Navigation** - Click to jump to specific moments in audio/video files

## Tech Stack

**Backend:**
- Java 21
- Spring Boot 3.5.10
- PostgreSQL 16
- Apache PDFBox 3.0.1
- Google Gemini AI API

**Frontend:**
- React 18
- Vite 5
- Axios

**DevOps:**
- Docker & Docker Compose
- GitHub Actions CI/CD
- JaCoCo for test coverage

## Prerequisites

- Java 21+
- Node.js 20+
- Docker & Docker Compose
- Google Gemini API Key (free at https://aistudio.google.com/app/apikey)

## Quick Start

### 1. Clone Repository
```bash
git clone https://github.com/yourusername/docqa-full.git
cd docqa-full
```

### 2. Setup Environment

Create `.env` file:
```env
GEMINI_API_KEY=your-gemini-api-key-here
```

### 3. Run with Docker Compose
```bash
docker compose up --build
```

### 4. Run Locally (Development)

**Backend:**
```bash
cd docqa
export GEMINI_API_KEY="your-key-here"
mvn spring-boot:run
```

**Frontend:**
```bash
cd docqa-frontend
npm install
npm run dev
```

## API Endpoints

| Method |         Endpoint           |    Description     |
|--------|----------------------------|--------------------|
| POST   | `/api/documents/upload`    | Upload a document  |
| GET    | `/api/documents`           | Get all documents  |
| GET    | `/api/documents/{id}`      | Get document by ID |
| POST   | `/api/documents/chat`      | Ask a question     |
| GET    | `/api/documents/{id}/file` | Download file      |

## Testing
```bash
cd docqa
mvn test

mvn jacoco:report
open target/site/jacoco/index.html
```

## AWS Deployment

### Using EC2
```bash
# SSH into EC2
ssh -i your-key.pem ubuntu@

# Install Docker
sudo apt update && sudo apt install -y docker.io docker-compose-v2
sudo usermod -aG docker ubuntu

# Clone and deploy
git clone https://github.com/yourusername/docqa-full.git
cd docqa-full
echo "GEMINI_API_KEY=your-key" > .env
docker compose up -d --build
```

Access: `http://<ec2-public-ip>`

## Project Structure
```
docqa-full/
├── docqa/                      # Spring Boot Backend
│   ├── src/main/java/
│   ├── src/test/java/
│   ├── pom.xml
│   └── Dockerfile
├── docqa-frontend/             # React Frontend
│   ├── src/
│   ├── package.json
│   ├── vite.config.js
│   └── Dockerfile
├── docker-compose.yml
├── .github/workflows/
└── README.md
```

## CI/CD

GitHub Actions automatically:
- Runs tests on every push
- Generates code coverage reports
- Builds frontend
- Validates Docker builds
