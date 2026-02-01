import { useState, useEffect } from 'react';
import { getAllDocuments } from '../api/api';

function DocumentList({ selectedDocument, onSelectDocument, refresh }) {
  const [documents, setDocuments] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDocuments();
  }, [refresh]);

  const loadDocuments = async () => {
    try {
      const docs = await getAllDocuments();
      setDocuments(docs);
    } catch (error) {
      console.error('Failed to load documents:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <p>Loading documents...</p>;

  return (
    <div style={{ padding: '20px', border: '1px solid #ccc', marginBottom: '20px' }}>
      <h2>Documents</h2>
      {documents.length === 0 ? (
        <p>No documents uploaded yet</p>
      ) : (
        <ul style={{ listStyle: 'none', padding: 0 }}>
          {documents.map((doc) => (
            <li
              key={doc.id}
              onClick={() => onSelectDocument(doc)}
              style={{
                padding: '10px',
                margin: '5px 0',
                backgroundColor: selectedDocument?.id === doc.id ? '#e0e0e0' : '#f9f9f9',
                cursor: 'pointer',
                border: '1px solid #ddd',
              }}
            >
              <strong>{doc.filename}</strong> ({doc.fileType})
              <br />
              <small>{new Date(doc.uploadedAt).toLocaleString()}</small>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default DocumentList;