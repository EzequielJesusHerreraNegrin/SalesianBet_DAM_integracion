import {
  createContext,
  ReactNode,
  useContext,
  useEffect,
  useState,
} from "react";
import { AuthenticatedUser, UserRequest } from "../type/User";
import { LocalStorageService } from "../service/localstorage.service";
import { Role } from "../type/Role";
import axios from "axios";
import { API_URL } from "../service/api";

interface AuthContextType {
  user: AuthenticatedUser | null;
  loginUser: (user: UserRequest) => Promise<string | undefined>;
  registerUser: (user: UserRequest) => Promise<any>;
  logout: () => void;
  isLogin: boolean;
  setIsLogin: (value: boolean) => void;
  setUser: (user: AuthenticatedUser) => void;
  isAdmin: boolean;
  setIsAdmin: (value: boolean) => void;
  refreshUser: () => void;
}

export const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<AuthenticatedUser | null>(null);
  const [isLogin, setIsLogin] = useState<boolean>(true);
  const [isAdmin, setIsAdmin] = useState<boolean>(false);

  useEffect(() => {
    const initialaizeUser = async () => {
      const token = await LocalStorageService.get(
        LocalStorageService.KEY.userToken
      );
      if (token) {
        const userLogged = await getUserLogged();
        if (userLogged) {
          setUser(userLogged.data);

          const isAdminUser = userLogged.data.roles.some(
            (role: Role) => role.roleName == "ADMIN"
          );
          setIsAdmin(isAdminUser);
          setIsLogin(true);
        } else {
          setIsLogin(false);
          setUser(null);
          setIsAdmin(false);
        }
      } else {
        setIsLogin(false);
      }
    };

    initialaizeUser();
  }, []);

  const loginUser = async (userRequest: UserRequest) => {
    try {
      const response = await axios.post(`${API_URL}/auth/login`, {
        email: userRequest.email,
        password: userRequest.password,
      });

      const jsonValue = response.data.data.accessToken;

      await LocalStorageService.save(
        LocalStorageService.KEY.userToken,
        jsonValue
      );

      const userLogged = await getUserLogged();

      if (userLogged) {
        setUser(userLogged.data);

        const isAdminUser = userLogged.data.roles.some(
          (role: Role) => role.roleName == "ADMIN"
        );
        setIsAdmin(isAdminUser!);
      }

      return jsonValue;
    } catch (error) {
      console.error(`Error in login: ${error}`);
    }
  };

  const registerUser = async (userRequest: UserRequest) => {
    const response = await axios.post(`${API_URL}/auth/register`, {
      email: userRequest.email,
      userName: userRequest.userName,
      dni: userRequest.dni,
      country: userRequest.country,
      password: userRequest.password,
    });
    console.log(response.data);
    return response.data;
  };

  const logout = async () => {
    await LocalStorageService.remove(LocalStorageService.KEY.userToken);
    setUser(null);
    setIsAdmin(false);
  };

  const getUserLogged = async () => {
    try {
      const token = await LocalStorageService.get(
        LocalStorageService.KEY.userToken
      );
      const response = await axios.get(`${API_URL}/users/me`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error: any) {
      if (error.response && error.response.status == 401) {
        throw new Error("UNAUTHORIZED");
      }
      throw error;
    }
  };

  const refreshUser = async () => {
    const userLogged = await getUserLogged(); // Puedes mover esta función a exportarla si hace falta
    if (userLogged) {
      setUser(userLogged.data);
    }
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        setUser,
        loginUser,
        registerUser,
        logout,
        isLogin,
        setIsLogin,
        isAdmin,
        setIsAdmin,
        refreshUser,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuthContext = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useDataContext must be used within a DataProvider");
  }
  return context;
};
