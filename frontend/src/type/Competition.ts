export type Competition = {
  competitionId: number;
  name: string;
  country: string;
};

const initialCompetition: Competition = {
  competitionId: 0,
  name: "",
  country: "",
};

export default initialCompetition;
