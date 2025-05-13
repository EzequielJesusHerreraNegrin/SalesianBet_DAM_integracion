import { useNavigate } from "react-router-dom";
import { Match, MatchRequest } from "../../../type/Match";
import toast, { Toaster } from "react-hot-toast";
import MatchService from "../../../service/match.service";
import { useEffect, useState } from "react";
import { Competition } from "../../../type/Competition";
import { Team } from "../../../type/Team";
import { CompetitionService } from "../../../service/competition.service";
import { TeamService } from "../../../service/team.service";
import "./MatchForm.css";
import { Link } from "react-router-dom";

export interface formMatchProps {
  currentMatch: Match;
  setCurrentMatch: (match: Match) => void;
  isCreating: boolean;
  matchesReady: boolean;
  setIsCreating: (value: boolean) => void;
}

const MatchModal = ({
  setIsCreating,
  isCreating,
  currentMatch,
  setCurrentMatch,
  matchesReady,
}: formMatchProps) => {
  const [competitions, setCompetitions] = useState<Competition[]>([]);
  const [teams, setTeams] = useState<Team[]>([]);

  const teamHomeLogo = `./src/assets/${currentMatch.competition.name
    .toLowerCase()
    .replace(/\s+/g, "")}/${currentMatch.homeTeam.teamName
    .toLowerCase()
    .replace(/\s+/g, "")}.png`;

  const teamAwayLogo = `./src/assets/${currentMatch.competition.name
    .toLowerCase()
    .replace(/\s+/g, "")}/${currentMatch.awayTeam.teamName
    .toLowerCase()
    .replace(/\s+/g, "")}.png`;

  useEffect(() => {
    console.log("currentMatch actualizado:", currentMatch);
  }, [currentMatch]);

  useEffect(() => {
    const fetchData = async () => {
      const competitions = await CompetitionService.getAllCompetitions();
      const teams = await TeamService.getAllTeams();
      setCompetitions(competitions);
      setTeams(teams);
    };

    fetchData();
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentMatch({
      ...currentMatch,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const { date, competition, homeTeam, awayTeam } = currentMatch;

    if (
      date.trim() == "" ||
      competition.competitionId == 0 ||
      homeTeam.teamId == 0 ||
      awayTeam.teamId == 0
    ) {
      toast.error("Los campos marcados son obligatorios", {
        position: "top-right",
      });
      return;
    }

    if (homeTeam.teamId === awayTeam.teamId) {
      toast.error("El equipo local y visitante no pueden ser el mismo", {
        position: "top-right",
      });
      return;
    }

    try {
      const request: MatchRequest = {
        date: currentMatch.date,
        competitionId: competition.competitionId,
        homeTeamId: homeTeam.teamId,
        awayTeamId: awayTeam.teamId,
      };
      if (isCreating) {
        await MatchService.createMatch(request);
        toast.success("Partido creado correctamente", {
          position: "top-right",
        });
      } else if (matchesReady) {
        await MatchService.validateMatch(
          currentMatch.matchId,
          currentMatch.result
        );
        toast.success("Partido validado correctamente", {
          position: "top-right",
        });
      } else {
        await MatchService.updateMatch(currentMatch.matchId, request);
        toast.success("Partido actualizado correctamente", {
          position: "top-right",
        });
      }

      setTimeout(() => {
        setIsCreating(false);
      }, 1000);
    } catch (error) {
      console.log("Error a la hora de crear el partido: ", error);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="formMatch-container">
        <Toaster />
        <h2 className="formMatch-title">Nuevo partido</h2>
        <form onSubmit={handleSubmit}>
          {matchesReady ? (
            <div className="formMatch-table-row">
              <div className="formMatch-edit-match">
                <div className="match-cell home">
                  <img
                    src={teamHomeLogo}
                    alt=""
                    style={{ width: "100px", height: "100px" }}
                  />
                  <span>{currentMatch.homeTeam.teamName}</span>
                </div>
                <input
                  type="text"
                  name="result"
                  id="result"
                  placeholder="Ej: 2-1"
                  value={currentMatch.result || ""}
                  onChange={(e) =>
                    setCurrentMatch({ ...currentMatch, result: e.target.value })
                  }
                  className="match-result-input"
                />
                <div className="match-cell away">
                  {currentMatch.awayTeam.teamName}
                  <img
                    src={teamAwayLogo}
                    alt="awayTeam"
                    style={{ width: "100px", height: "100px" }}
                  />
                </div>
              </div>
              <div className="formMatch-buttons">
                <button className="formMatch-button" type="submit">
                  Validar resultado
                </button>
                <Link to={"/"} className="formMatch-button-cancel">
                  Cancelar
                </Link>
              </div>
            </div>
          ) : (
            <>
              <div className="formMatch-input">
                <label htmlFor="date">Fecha</label>
                <input
                  type="datetime-local"
                  name="date"
                  id="date"
                  onChange={handleChange}
                  value={currentMatch.date}
                />
              </div>
              <div className="formMatch-select">
                <label htmlFor="competition">Competición</label>
                <select
                  name="competition"
                  value={currentMatch.competition?.competitionId || ""}
                  onChange={(e) => {
                    const selectedId = Number(e.target.value);
                    const selected = competitions.find(
                      (c) => c.competitionId === selectedId
                    );
                    if (!selected) return;
                    setCurrentMatch({ ...currentMatch, competition: selected });
                  }}
                >
                  <option value="">Selecciona la competición</option>
                  {competitions.map((c) => (
                    <option key={c.competitionId} value={c.competitionId}>
                      {c.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="formMatch-teams">
                <div className="formMatch-select">
                  <label htmlFor="homeTeam">Equipo Local</label>
                  <select
                    name="homeTeam"
                    value={currentMatch.homeTeam.teamId}
                    onChange={(e) => {
                      const selectedId = Number(e.target.value);
                      const selected = teams.find(
                        (t) => t.teamId === selectedId
                      );
                      if (!selected) return;
                      if (selected.teamId === currentMatch.awayTeam.teamId) {
                        toast.error(
                          "El equipo local no puede ser igual que el visitante"
                        );
                        return;
                      }
                      setCurrentMatch({ ...currentMatch, homeTeam: selected });
                    }}
                  >
                    <option value={0}>Selecciona el equipo</option>
                    {teams.map((team) => (
                      <option key={team.teamId} value={team.teamId}>
                        {team.teamName}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="formMatch-select">
                  <label htmlFor="awayTeam">Equipo Visitante</label>
                  <select
                    name="awayTeam"
                    value={currentMatch.awayTeam.teamId}
                    onChange={(e) => {
                      const selectedId = Number(e.target.value);
                      const selected = teams.find(
                        (t) => t.teamId === selectedId
                      );
                      if (!selected) return;
                      if (selected.teamId === currentMatch.homeTeam.teamId) {
                        toast.error(
                          "El equipo visitante no puede ser igual que el local"
                        );
                        return;
                      }
                      setCurrentMatch({ ...currentMatch, awayTeam: selected });
                    }}
                  >
                    <option value={0}>Selecciona el equipo</option>
                    {teams.map((team) => (
                      <option key={team.teamId} value={team.teamId}>
                        {team.teamName}
                      </option>
                    ))}
                  </select>
                </div>
              </div>

              <div className="formMatch-buttons">
                <button className="formMatch-button" type="submit">
                  {isCreating ? "Crear partido" : "Editar partido"}
                </button>
                <Link to={"/"} className="formMatch-button-cancel">
                  Cancelar
                </Link>
              </div>
            </>
          )}
        </form>
      </div>
    </div>
  );
};

export default MatchModal;
