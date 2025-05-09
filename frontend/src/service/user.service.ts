import axios from "axios";
import { API_URL } from "./match.service";
import { UserRequestRegister } from "../type/User";

const createUser = async (user: UserRequestRegister) => {
  const response = await axios.post(`${API_URL}/users`, user);
  return response.data;
};

const UserService = {
  createUser,
};

export default UserService;
