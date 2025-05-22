import { BetRequest } from "../type/Bet";
import api from "./api";

const createBet = async (betRquest: BetRequest) => {
  const response = await api.post(`/bets`, betRquest);
  return response.data;
};

const deleteBet = async (betId: number) => {
  const response = await api.post(`/bets/${betId}`);
  return response.data;
};

const getBetByUserAndMatch = async (userId: number, matchId: number) => {
  const response = await api.get(`/bets/users/${userId}/matches/${matchId}`);
  return response.data.data;
};

const updateBet = async (betId: number, betRequest: BetRequest) => {
  const response = await api.put(`/bets/${betId}`, betRequest);
  return response.data;
};

const BetService = {
  createBet,
  deleteBet,
  getBetByUserAndMatch,
  updateBet,
};

export default BetService;
