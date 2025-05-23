import { Match } from "./Match";
import { AuthenticatedUser } from "./User";

export type Bet = {
  betId: number;
  points: number;
  prediction: string;
  match: Match;
  user: AuthenticatedUser;
};

export type BetRequest = {
  prediction: string;
  points: number;
  matchId: number;
  userId: number;
};
