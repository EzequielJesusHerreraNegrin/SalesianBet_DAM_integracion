import axios from "axios";
import { BetRequest } from "../type/Bet";
import { API_URL } from "./match.service";

const createBet = async (betRquest: BetRequest) => {
  const response = await axios.post(`${API_URL}/bets`, betRquest);
  return response.data;
};

const BetService = {
  createBet,
};

export default BetService;
