import axios from "axios";
import { API_URL } from "./match.service";
import { LocalStorageService } from "./localstorage.service";

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

export default api