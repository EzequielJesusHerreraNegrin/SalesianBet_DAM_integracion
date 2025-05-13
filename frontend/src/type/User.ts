import { Bet } from "./Bet";
import { Role } from "./Role";

export type AuthenticatedUser = {
  userId: number;
  userName: string;
  email: string;
  dni: string;
  bets: Bet[];
  token: string;
  points: number;
  roles: Role;
};

export const initialUserRequest: UserRequest = {
  userName: "",
  password: "",
  dni: "",
  country: "",
  email: "",
  token: "",
};

export type UserRequest = {
  userName?: string;
  password: string;
  dni?: string;
  country?: string;
  email: string;
  token: string;
};
