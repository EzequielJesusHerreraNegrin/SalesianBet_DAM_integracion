import axios from "axios";
import { API_URL } from "./api";

const getAllTeams = async () => {
  const response = await axios.get(`${API_URL}/teams`);
  return response.data.data;
};

export const TeamService = {
  getAllTeams,
};
