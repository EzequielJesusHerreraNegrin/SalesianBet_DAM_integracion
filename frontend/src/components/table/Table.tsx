import { useEffect, useState } from "react";
import initialMatch, { Match } from "../../type/Match";
import MatchService from "../../service/match.service";
import Loader from "../loader/Loader";
import Message from "../message/Message";
import "./Table.css";
import { Link } from "react-router-dom";

interface TableProps {
  selectedDate: string;
  formatDate: (isoDate: string) => { date: string; time: string };
  setCurrentMatch: (match: Match) => void;
  setIsCreating: (value: boolean) => void;
}
const Table = ({
  setIsCreating,
  selectedDate,
  formatDate,
  setCurrentMatch,
}: TableProps) => {
  const [matches, setMatches] = useState<Match[]>([]);
  const [error, setError] = useState<String | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  const handleAddMatch = () => {
    setIsCreating(true);
    setCurrentMatch(initialMatch);
  };

  const handleEditMatch = (match: Match) => {
    setIsCreating(false);
    setCurrentMatch(match);
  };

  const fetchMatchesByDate = async (isoDate: String) => {
    try {
      setLoading(true);
      const data = await MatchService.getMatchesByDate(isoDate);
      console.log(data);
      setMatches(data);
      setError(null);
    } catch ({ err }: any) {
      console.error(err);
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    // console.log(selectedDate)
    fetchMatchesByDate(selectedDate);
  }, [selectedDate]);

  if (loading) return <Loader />;
  if (error) return <Message />;

  return (
    <div className="table-container">
      <div className="table-button-container">
        <Link to={"/form"} onClick={handleAddMatch} className="table-button">
          + Crear partido
        </Link>
      </div>
      {(() => {
        let lastCompetition = "";

        return matches.map((match) => {
          const time = formatDate(match.date).time;
          const currentCompetition = `${match.competition?.country} ${match.competition?.name}`;
          const showCompetitionTitle = currentCompetition !== lastCompetition;
          if (showCompetitionTitle) lastCompetition = currentCompetition;

          return (
            <div
              key={match.matchId}
              className={` ${
                showCompetitionTitle ? "competition-separator" : ""
              }`}
            >
              {showCompetitionTitle && (
                <div className="table-competition">
                  <h4 className="table-header">{currentCompetition}</h4>
                </div>
              )}

              <div className="table-row">
                <div className="match-cell home">{match.homeTeam.teamName}</div>
                <div className="match-cell center">
                  {match.result === null || match.result === ""
                    ? time
                    : match.result}
                </div>
                <div className="match-cell away">{match.awayTeam.teamName}</div>
                <Link to={"/form"} onClick={() => handleEditMatch(match)}>
                  edit
                </Link>
              </div>
            </div>
          );
        });
      })()}
    </div>
  );
};

export default Table;
