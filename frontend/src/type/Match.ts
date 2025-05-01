import initialCompetition, { Competition } from "./Competition";
import initialTeam, { Team } from "./Team";

export type Match = {
  matchId: number;
  date: string;
  result: string;
  competition: Competition;
  homeTeam: Team;
  awayTeam: Team;
};

const initialMatch: Match = {
  matchId: 0,
  date: "",
  result: "",
  competition: initialCompetition,
  homeTeam: initialTeam,
  awayTeam: initialTeam,
};

export type MatchRequest = {
  date: string;
  result?: string;
  competitionId: number;
  homeTeamId: number;
  awayTeamId: number;
};

export const initialMatchRequest: MatchRequest = {
  date: "",
  result: "",
  competitionId: 0,
  homeTeamId: 0,
  awayTeamId: 0,
};

export default initialMatch;
