import axios from 'axios';

const api = axios.create({
  baseURL: '/api', // Usa o proxy configurado
  timeout: 5000 // Tempo máximo de espera
});

export default api;
