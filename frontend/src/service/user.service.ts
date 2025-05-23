import axios from "axios";

import { jwtDecode } from "jwt-decode";
import { ApiResponseDto, JwtPayload } from "../types/api";
import { UserResponseDto } from "../types/User";
import api, { API_URL } from "./api";
import { LocalStorageService } from "./localstorage.service";

const token = localStorage.getItem("token");

const getAllUsers = async () => {
  const response = await api.get(`/users`);
  return response.data.data;
};

const getUserByEmail = async (email: string) => {
  const response = await api.get(`/users/email`, {
    params: {
      email,
    },
  });
  return response.data.data;
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

const buyCartItems = async (
  userId: number
): Promise<ApiResponseDto<boolean>> => {
  try {
    const token = await LocalStorageService.get(
      LocalStorageService.KEY.userToken
    );

    const response = await axios.post<ApiResponseDto<boolean>>(
      `${API_URL}/users/purchase/${userId}`,
      {},
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );

    console.log("Response from buyCartItems: ", response.data);
    return response.data;
  } catch (error) {
    console.error(`Error processing purchase for user ${userId}:`, error);
    throw error;
  }
};

const manageUserToken = async () => {
  const token = await LocalStorageService.get(
    LocalStorageService.KEY.userToken
  );
  if (!token) return null;
  const decodedToken = jwtDecode<JwtPayload>(token);

  return decodedToken;
};
const UserService = {
  getCurrentUser,
  manageUserToken,
  buyCartItems,
  getAllUsers,
  getUserByEmail,
};

export default UserService;
