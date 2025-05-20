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

const BetService = {
  createBet,
  deleteBet,
};

export default BetService;
