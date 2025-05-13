import axios from "axios";
import { MatchRequest } from "../type/Match";
export const API_URL = "http://localhost:8081/api/v1";

const getMatchesByDate = async (date: String) => {
  const response = await axios.get(`${API_URL}/matches/by-date`, {
    params: { date },
  });
  return response.data.data;
};

const getMatchById = async (matchId: number) => {
  const response = await axios.get(`${API_URL}/matches/${matchId}`);
  return response.data;
};

const createMatch = async (match: MatchRequest) => {
  const response = await axios.post(`${API_URL}/matches`, match);
  return response.data;
};

const updateMatch = async (matchId: number, match: MatchRequest) => {
  const response = await axios.put(`${API_URL}/matches/${matchId}`, match);
  return response.data;
};

const getMatchesReadyToValidate = async () => {
  const response = await axios.get(`${API_URL}/matches/ready`);
  return response.data.data;
};

const validateMatch = async (matchId: number, result: string) => {
  const response = await axios.put(`${API_URL}/matches/${matchId}/validate`, {
    result,
  });
  return response.data;
};

const MatchService = {
  getMatchesByDate,
  getMatchById,
  createMatch,
  updateMatch,
  getMatchesReadyToValidate,
  validateMatch,
};

export default MatchService;
