import axios from "axios";
import { MatchRequest } from "../type/Match";
export const API_URL = "http://172.18.176.1:8081/api/v1";

const getMatchesByDate = async (date: String) => {
  const response = await axios.get(`${API_URL}/matches/by-date`, {
    params: { date },
  });
  return response.data.data;
};

const getMatchById = async (idMatch: number) => {
  const response = await axios.get(`${API_URL}/matches/${idMatch}`);
  return response.data;
};

const createMatch = async (match: MatchRequest) => {
  const response = await axios.post(`${API_URL}/matches`, match);
  return response.data;
};

const MatchService = {
  getMatchesByDate,
  getMatchById,
  createMatch,
};

export default MatchService;
