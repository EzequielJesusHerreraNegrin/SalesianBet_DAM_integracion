import axios from "axios";
import { API_URL } from "./api";

const getAllCompetitions = async () => {
  const response = await axios.get(`${API_URL}/competitions`);
  return response.data.data;
};

export const CompetitionService = {
  getAllCompetitions,
};
