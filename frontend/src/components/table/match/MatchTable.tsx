import { useEffect, useState } from "react";
import initialMatch, { Match } from "../../../type/Match";
import MatchService from "../../../service/match.service";
import Loader from "../../loader/Loader";
import "./MatchTable.css";
import { useNavigate } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import MonetizationOnIcon from "@mui/icons-material/MonetizationOn";
import DateCalendar from "../../calendar/DateCalendar";
import { useAuthContext } from "../../../context/AuthContext";

interface TableProps {
  selectedDate: string;
  setSelectedDate: (date: string) => void;
  formatDate: (isoDate: string) => { date: string; time: string };
  setCurrentMatch: (match: Match) => void;
  setIsCreating: (value: boolean) => void;
  matchesReady: boolean;
  setMatchesReady: (value: boolean) => void;
  setIsBetting: (value: boolean) => void;
}

const MatchTable = ({
  setIsCreating,
  selectedDate,
  formatDate,
  setCurrentMatch,
  matchesReady,
  setMatchesReady,
  setIsBetting,
  setSelectedDate,
}: TableProps) => {
  const { user, isAdmin } = useAuthContext();
  const [matches, setMatches] = useState<Match[]>([]);
  const [loading, setLoading] = useState<boolean>(false);

  const navigate = useNavigate();

  const handleAddMatch = () => {
    setMatchesReady(false);
    setIsCreating(true);
    setCurrentMatch(initialMatch);
    navigate("/matchForm");
  };

  const handleEditMatch = (match: Match) => {
    setIsCreating(false);
    setCurrentMatch(match);
    navigate("/matchForm");
  };

  const handleClickMatchesReady = () => {
    setMatchesReady(!matchesReady);
    if (!isAdmin) {
      setMatchesReady(false);
    }
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

  const handleBetMatch = (match: Match) => {
    setIsBetting(true);
    setCurrentMatch(match);
  };

  useEffect(() => {
    fetchMatches(selectedDate);
  }, [selectedDate, matchesReady]);

  const renderMatches = () => {
    let lastCompetition = "";

    return matches.map((match) => {
      const currentCompetition = `${match.competition?.name}`;
      const teamHomeLogo = `./src/assets/${currentCompetition
        .toLowerCase()
        .replace(/\s+/g, "")}/${match.homeTeam.teamName
        .toLowerCase()
        .replace(/\s+/g, "")}.png`;
      const teamAwayLogo = `./src/assets/${currentCompetition
        .toLowerCase()
        .replace(/\s+/g, "")}/${match.awayTeam.teamName
        .toLowerCase()
        .replace(/\s+/g, "")}.png`;
      console.log(match);
      const matchDate = new Date(match.date);
      const now = new Date();
      const canBet = now < matchDate && match.result === "";
      const time = formatDate(match.date).time;
      const showCompetitionTitle = currentCompetition !== lastCompetition;
      if (showCompetitionTitle) lastCompetition = currentCompetition;

      return (
        <div
          key={match.matchId}
          className={`${showCompetitionTitle ? "competition-separator" : ""}`}
        >
          {showCompetitionTitle && (
            <div className="table-competition">
              <img
                style={{ borderRadius: "50%", marginRight: "20px" }}
                src={`./src/assets/pais/${match.competition?.country.toLowerCase()}.png`}
                alt="country"
              />
              <h4 className="table-header">{currentCompetition}</h4>
            </div>
          )}
          <div className="table-row">
            <div className="match-cell-table home">
              {match.homeTeam.teamName}
              <img src={teamHomeLogo} alt="homeTeam" />
            </div>
            <div className="match-cell-table center">
              {match.result ? match.result : time}
            </div>
            <div className="match-cell-table away">
              <img src={teamAwayLogo} alt="awayTeam" />
              {match.awayTeam.teamName}
            </div>
            <div className="table-action-cell">
              {isAdmin ? (
                <button
                  className="table-button-edit"
                  onClick={() => handleEditMatch(match)}
                >
                  <EditIcon sx={{ width: "30px", height: "30px" }} />
                </button>
              ) : (
                user &&
                canBet && (
                  <button
                    className="table-button-bet"
                    onClick={() => handleBetMatch(match)}
                  >
                    <MonetizationOnIcon
                      color="warning"
                      sx={{
                        backgroundColor: "green",
                        borderRadius: "50%",
                        border: "3px solid",
                        width: "30px",
                        height: "30px",
                      }}
                    />
                  </button>
                )
              )}
            </div>
          </div>
        </div>
      );
    });
  };

  if (loading)
    return (
      <div style={{ display: "flex", justifyContent: "center" }}>
        <Loader />
      </div>
    );

  return (
    <>
      <div className="table-container">
        <div className="table-button-container">
          {isAdmin && (
            <>
              <button onClick={handleAddMatch} className="table-button">
                + Crear partido
              </button>
              <button
                className="table-button"
                onClick={handleClickMatchesReady}
                style={{
                  backgroundColor: matchesReady
                    ? "rgba(255, 0, 0, 0.671)"
                    : "#2f9e44",
                }}
              >
                {matchesReady ? "Salir" : "Partidos a validar"}
              </button>
            </>
          )}

          <DateCalendar onSelectDate={setSelectedDate} />
        </div>
        {renderMatches()}
      </div>
    </>
  );
};

export default MatchTable;
