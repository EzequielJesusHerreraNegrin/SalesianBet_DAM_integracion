import { Match } from "./Match";
import { User } from "./User";

export type Bet = {
  betId: number;
  points: number;
  prediction: string;
  match: Match;
  user: User;
};

export type BetRequest = {
  prediction: string;
  points: number;
  matchId: number;
  userId: number;
};
