import axios from "axios";
import { LocalStorageService } from "./localstorage.service";
export const API_URL = "http://localhost:8081/api/v1";

const api = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  async (config) => {
    const token = await LocalStorageService.get(
      LocalStorageService.KEY.userToken
    );

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },

  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response.status == 401) {
      await LocalStorageService.remove(LocalStorageService.KEY.userToken);
      window.location.reload();
    }
    return Promise.reject(error);
  }
);

export default api;
