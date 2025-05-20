/* import axios from "axios";
import { API_URL } from "./match.service";
import { UserRequest } from "../type/User";

const createUser = async (user: UserRequest) => {
  const response = await axios.post(`${API_URL}/users`, user);
  return response.data;
};

const UserService = {
  createUser,
};

export default UserService;
 */

import { jwtDecode } from "jwt-decode";
import { LocalStorageService } from "./localstorage.service";

export const manageUserToken = async () => {
  const token = await LocalStorageService.get(
    LocalStorageService.KEY.userToken
  );
  if (!token) return null;
  const decodedToken = jwtDecode(token);

  return decodedToken;
};

export const manageUserRole = async () => {
  const token = await LocalStorageService.get(
    LocalStorageService.KEY.userToken
  );
  if (!token) return null;
  const decodedToken = jwtDecode(token);
  const role = decodedToken["role"];

  return role;
};
