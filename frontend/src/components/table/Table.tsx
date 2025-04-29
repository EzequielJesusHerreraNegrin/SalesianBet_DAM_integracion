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
}
const Table = ({ selectedDate, formatDate, setCurrentMatch }: TableProps) => {
  const [matches, setMatches] = useState<Match[]>([]);
  const [error, setError] = useState<String | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  const handleAddMatch = () => {
    setCurrentMatch(initialMatch);
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
              key={match.idMatch}
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
                {match.result === ""
                  ? `${match.homeTeam.teamName} ${time} ${match.awayTeam.teamName}`
                  : `${match.homeTeam.teamName} ${match.result} ${match.awayTeam.teamName}`}
              </div>
            </div>
          );
        });
      })()}
    </div>
  );
};

export default Table;
