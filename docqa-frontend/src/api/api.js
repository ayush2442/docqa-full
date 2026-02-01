import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

export const uploadDocument = async (file) => {
  const formData = new FormData();
  formData.append('file', file);
  
  const response = await axios.post(`${API_BASE_URL}/documents/upload`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  
  return response.data;
};

export const getAllDocuments = async () => {
  const response = await axios.get(`${API_BASE_URL}/documents`);
  return response.data;
};

export const getDocument = async (id) => {
  const response = await axios.get(`${API_BASE_URL}/documents/${id}`);
  return response.data;
};

export const chat = async (documentId, question) => {
  const response = await axios.post(`${API_BASE_URL}/documents/chat`, {
    documentId,
    question,
  });
  return response.data;
};