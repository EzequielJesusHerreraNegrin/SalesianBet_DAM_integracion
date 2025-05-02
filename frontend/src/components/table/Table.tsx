import { useEffect, useState } from "react";
import initialMatch, { Match } from "../../type/Match";
import MatchService from "../../service/match.service";
import Loader from "../loader/Loader";
import "./Table.css";
import { useNavigate } from "react-router-dom";

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
  const [loading, setLoading] = useState<boolean>(false);
  const [matchesReady, setMatchesReady] = useState<boolean>(false);
  const navigate = useNavigate();

  const handleAddMatch = () => {
    setIsCreating(true);
    setCurrentMatch(initialMatch);
    navigate("/matchForm");
  };

  const handleEditMatch = (match: Match) => {
    setIsCreating(false);
    setCurrentMatch(match);
    navigate("/matchForm");
  };

  const fetchMatches = async (isoDate: string) => {
    setLoading(true);
    try {
      const data = matchesReady
        ? await MatchService.getMatchesReadyToValidate()
        : await MatchService.getMatchesByDate(isoDate);
      setMatches(data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMatches(selectedDate);
  }, [selectedDate, matchesReady]);

  if (loading) return <Loader />;

  return (
    <div className="table-container">
      <div className="table-button-container">
        <button onClick={handleAddMatch} className="table-button">
          + Crear partido
        </button>
        <button
          className="table-button"
          onClick={() => setMatchesReady((prev) => !prev)}
          style={{
            backgroundColor: matchesReady
              ? "rgba(255, 0, 0, 0.671)"
              : "#2f9e44",
          }}
        >
          {matchesReady ? "Salir" : "Partidos a validar"}
        </button>
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
              className={`${
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
                  {match.result ? match.result : time}
                </div>
                <div className="match-cell away">{match.awayTeam.teamName}</div>
                <button onClick={() => handleEditMatch(match)}>
                  {matchesReady ? "Validar" : "Editar"}
                </button>
              </div>
            </div>
          );
        });
      })()}
    </div>
  );
};

export default Table;
