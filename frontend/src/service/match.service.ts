import { MatchRequest } from "../types/Match";
import api from "./api";

const getMatchesByDate = async (date: string) => {
  const response = await api.get(`/matches/by-date`, {
    params: { date },
  });
  return response.data.data;
};

const getMatchById = async (matchId: number) => {
  const response = await api.get(`/matches/${matchId}`);
  return response.data;
};

const createMatch = async (match: MatchRequest) => {
  const response = await api.post(`/matches`, match);
  return response.data;
};

const updateMatch = async (matchId: number, match: MatchRequest) => {
  const response = await api.put(`/matches/${matchId}`, match);
  return response.data;
};

const getMatchesReadyToValidate = async () => {
  const response = await api.get(`/matches/ready`);
  return response.data.data;
};

const validateMatch = async (matchId: number, result: string) => {
  const response = await api.put(`/matches/${matchId}/validate`, {
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
