import {
  createContext,
  ReactNode,
  useContext,
  useEffect,
  useState,
} from "react";
import { AuthenticatedUser, UserRequest } from "../type/User";
import { LocalStorageService } from "../service/localstorage.service";
import api from "../service/api";
import { Role } from "../type/Role";

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
        try {
          const userLogged = await getUserLogged();
          if (userLogged) {
            setUser(userLogged.data);

            const isAdminUser = userLogged.data.roles.some(
              (role: Role) => role.roleName == "ADMIN"
            );
            setIsAdmin(isAdminUser);
            setIsLogin(true);
          }
        } catch (error) {
          console.error("Error initializing user:", error);
          // Token inválido o expirado → lo borro para limpiar la sesión
          await LocalStorageService.remove(LocalStorageService.KEY.userToken);
          setUser(null);
          setIsLogin(false);
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
      const response = await api.post(`/auth/login`, {
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
    const response = await api.post(`/auth/register`, {
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
      const response = await api.get(`/users/me`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error to get current user: ", error);
      return null;
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
