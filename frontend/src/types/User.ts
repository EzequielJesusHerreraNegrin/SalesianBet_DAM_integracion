import { Bet } from "./Bet";

export type User = {
  userId: number;
  userName: string;
  password: string;
  email: string;
  dni: string;
  bets: Bet[];
  // roles:
};

export interface UserEntityResponseDto {
  id: number; // Asumiendo que Long se mapea a number
  username: string;
}
