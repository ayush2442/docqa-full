import { useState } from 'react';
import Upload from './components/Upload';
import DocumentList from './components/DocumentList';
import DocumentView from './components/DocumentView';
import MediaPlayer from './components/MediaPlayer';
import Chat from './components/Chat';
import './App.css';

function App() {
  const [selectedDocument, setSelectedDocument] = useState(null);
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  const handleUploadComplete = (document) => {
    setRefreshTrigger((prev) => prev + 1);
    setSelectedDocument(document);
  };

  return (
    <div className="App">
      <h1>Document & Multimedia Q&A</h1>
      
      <div style={{ display: 'grid', gridTemplateColumns: '1fr 2fr', gap: '20px', padding: '20px' }}>
        <div>
          <Upload onUploadComplete={handleUploadComplete} />
          <DocumentList
            selectedDocument={selectedDocument}
            onSelectDocument={setSelectedDocument}
            refresh={refreshTrigger}
          />
        </div>
        
        <div>
          <DocumentView document={selectedDocument} />
          <MediaPlayer document={selectedDocument} />
          <Chat document={selectedDocument} />
        </div>
      </div>
    </div>
  );
}

export default App;