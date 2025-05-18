import { BetRequest } from "../type/Bet";
import { API_URL } from "./match.service";
import api from "./api";

const createBet = async (betRquest: BetRequest) => {
  const response = await api.post(`${API_URL}/bets`, betRquest);
  return response.data;
};

const BetService = {
  createBet,
};

export default BetService;
