import { BetRequest } from "../types/Bet";

import api, { API_URL } from "./api";

const createBet = async (betRquest: BetRequest) => {
  const response = await api.post(`${API_URL}/bets`, betRquest);
  return response.data;
};

const BetService = {
  createBet,
};

export default BetService;
