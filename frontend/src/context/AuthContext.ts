import { createContext, ReactNode, useState } from "react";
import { User } from "../type/User";
import axios from "axios";
import { API_URL } from "../service/match.service";
import { LocalStorageService } from "../service/localstorage.service";

interface AuthenticatedUser extends User {
    token: string;
  }

interface AuthContextType {
    user: AuthenticatedUser | null;
    loginUser: (user: User) => Promise<string | undefined>;
    registerUser: (user: User) => Promise<any>;
    logout: () => void;
  }

const AuthContext = createContext<AuthContextType | null>(null);

export const authProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);

  const loginUser = async (userRequest: User) => {
    try {
      const response = await axios.post(`${API_URL}/auth/login`, {
        email: userRequest.email,
        password: userRequest.password,
      });

      const jsonValue = response.data.object.token;
      await LocalStorageService.save(
        LocalStorageService.KEY.userToken,
        jsonValue
      );

      setUser({ ...userRequest, token: jsonValue });

      return jsonValue;
    } catch (error) {
      console.error(`Error in login: ${error}`);
    }
  };

  const registerUser = async (userRequest: User) => {
    const response = await axios.post(`${API_URL}/auth/register`, {
      email: userRequest.email,
      useName: userRequest.userName,
      dni: userRequest.dni,
      password: userRequest.password,
    });

    return response.data.json();
  };

 
};
