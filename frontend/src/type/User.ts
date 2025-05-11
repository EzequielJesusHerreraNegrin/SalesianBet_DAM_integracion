import { Bet } from "./Bet";

export type User = {
  userId: number;
  userName: string;
  password: string;
  email: string;
  dni: string;
  bets: Bet[];
  token: string
  // roles:
};

export type UserRequestRegister = {
  userName: string;
  password: string
  email: string
};

