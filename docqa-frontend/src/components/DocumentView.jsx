function DocumentView({ document }) {
  if (!document) {
    return <p>Select a document to view details</p>;
  }

  return (
    <div style={{ padding: '20px', border: '1px solid #ccc', marginBottom: '20px' }}>
      <h2>Document Details</h2>
      <p><strong>Filename:</strong> {document.filename}</p>
      <p><strong>Type:</strong> {document.fileType}</p>
      <p><strong>Uploaded:</strong> {new Date(document.uploadedAt).toLocaleString()}</p>
      
      {document.summary && (
        <div>
          <h3>Summary</h3>
          <p>{document.summary}</p>
        </div>
      )}
      
      {document.extractedText && (
        <div>
          <h3>Extracted Text</h3>
          <p style={{ maxHeight: '200px', overflow: 'auto', border: '1px solid #ddd', padding: '10px' }}>
            {document.extractedText}
          </p>
        </div>
      )}
    </div>
  );
}

export default DocumentView;