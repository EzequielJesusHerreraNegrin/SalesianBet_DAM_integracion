import { Competition } from "./Competition";
import { Team } from "./Team";

export type Match = {
  idMatch: number;
  date: string;
  result: string;
  competition: Competition;
  homeTeam: Team;
  awayTeam: Team;
};
