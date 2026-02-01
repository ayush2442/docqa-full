import { useState } from 'react';
import { chat } from '../api/api';

function Chat({ document }) {
  const [question, setQuestion] = useState('');
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(false);

  if (!document) {
    return <p>Select a document to start chatting</p>;
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!question.trim()) return;

    const userMessage = { type: 'user', text: question };
    setMessages([...messages, userMessage]);
    setQuestion('');
    setLoading(true);

    try {
      const response = await chat(document.id, question);
      
      const assistantMessage = {
        type: 'assistant',
        text: response.answer,
        timestamps: response.timestamps,
      };
      
      setMessages((prev) => [...prev, assistantMessage]);
    } catch (error) {
      const errorMessage = {
        type: 'error',
        text: 'Failed to get response: ' + (error.response?.data?.message || error.message),
      };
      setMessages((prev) => [...prev, errorMessage]);
    } finally {
      setLoading(false);
    }
  };

  const handleTimestampClick = (timeInSeconds) => {
    if (window.seekToTimestamp) {
      window.seekToTimestamp(timeInSeconds);
    }
  };

  return (
    <div style={{ padding: '20px', border: '1px solid #ccc' }}>
      <h2>Chat with Document</h2>
      
      <div style={{ height: '300px', overflow: 'auto', border: '1px solid #ddd', padding: '10px', marginBottom: '10px' }}>
        {messages.length === 0 ? (
          <p>Ask a question about the document...</p>
        ) : (
          messages.map((msg, index) => (
            <div key={index} style={{ marginBottom: '15px' }}>
              <strong>{msg.type === 'user' ? 'You' : msg.type === 'error' ? 'Error' : 'Assistant'}:</strong>
              <p style={{ margin: '5px 0' }}>{msg.text}</p>
              
              {msg.timestamps && msg.timestamps.length > 0 && (
                <div style={{ marginTop: '10px', padding: '10px', backgroundColor: '#f0f0f0' }}>
                  <strong>Related Timestamps:</strong>
                  {msg.timestamps.map((ts, idx) => (
                    <div key={idx} style={{ marginTop: '5px' }}>
                      <button
                        onClick={() => handleTimestampClick(ts.timeInSeconds)}
                        style={{
                          marginRight: '10px',
                          padding: '5px 10px',
                          cursor: 'pointer',
                          backgroundColor: '#007bff',
                          color: 'white',
                          border: 'none',
                          borderRadius: '3px',
                        }}
                      >
                        ▶ {Math.floor(ts.timeInSeconds / 60)}:{Math.floor(ts.timeInSeconds % 60).toString().padStart(2, '0')}
                      </button>
                      <span>{ts.text}</span>
                    </div>
                  ))}
                </div>
              )}
            </div>
          ))
        )}
        {loading && <p>Thinking...</p>}
      </div>
      
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="Ask a question..."
          style={{ width: '80%', padding: '10px' }}
          disabled={loading}
        />
        <button type="submit" disabled={loading} style={{ width: '18%', padding: '10px', marginLeft: '2%' }}>
          {loading ? 'Sending...' : 'Send'}
        </button>
      </form>
    </div>
  );
}

export default Chat;