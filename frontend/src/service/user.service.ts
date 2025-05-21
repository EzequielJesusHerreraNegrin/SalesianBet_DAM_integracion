import axios from "axios";

import { UserRequest, UserResponseDto } from "../types/User";
import { jwtDecode } from "jwt-decode";
import { LocalStorageService } from "./localstorage.service";
import { ApiResponseDto, JwtPayload } from "../types/api";
import { API_URL } from "./api";

const token = localStorage.getItem("token");

const createUser = async (user: UserRequest) => {
  const response = await axios.post(`${API_URL}/users`, user);
  return response.data;
};

const getCurrentUser = async (): Promise<ApiResponseDto<UserResponseDto>> => {
  try {
    const response = await axios.get<ApiResponseDto<UserResponseDto>>(
      `${API_URL}/users/me`,
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: token ? `Bearer ${token}` : "",
        },
      }
    );
    console.log("Response from getAllCartItemsByUserId: ", response.data.code);

    return response.data;
  } catch (error) {
    console.error("Error fetching all cart items:", error);
    throw error;
  }
};

const manageUserToken = async () => {
  const token = await LocalStorageService.get(
    LocalStorageService.KEY.userToken
  );
  if (!token) return null;
  const decodedToken = jwtDecode<JwtPayload>(token);
  console.log(decodedToken.userId);

  return decodedToken;
};
const UserService = {
  createUser,
  getCurrentUser,
  manageUserToken,
};

export default UserService;
