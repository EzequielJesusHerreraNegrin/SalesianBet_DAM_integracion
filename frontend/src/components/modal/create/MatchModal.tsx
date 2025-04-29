import { useNavigate } from "react-router-dom";
import initialMatch, { Match, MatchRequest } from "../../../type/Match";
import toast, { Toaster } from "react-hot-toast";
import MatchService from "../../../service/match.service";
import { useEffect, useState } from "react";
import initialCompetition, { Competition } from "../../../type/Competition";
import { Team } from "../../../type/Team";
import { CompetitionService } from "../../../service/competition.service";
import { TeamService } from "../../../service/team.service";

export interface formMatchProps {
  currentMatch: Match;
  setCurrentMatch: (match: Match) => void;
}

const MatchModal = ({ currentMatch, setCurrentMatch }: formMatchProps) => {
  const [competitions, setCompetitions] = useState<Competition[]>([]);
  const [teams, setTeams] = useState<Team[]>([]);
  const navigate = useNavigate();

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
        date: date,
        competitionId: competition.competitionId,
        homeTeamId: homeTeam.teamId,
        awayTeamId: awayTeam.teamId,
      };
      await MatchService.createMatch(request);
      toast.success("Partido creado correctamente", {
        position: "top-right",
      });

      setTimeout(() => {
        navigate("/matchesPage");
      }, 1000);
    } catch (error) {
      console.log("Error a la hora de crear el partido: ", error);
    }
  };

  return (
    <div>
      <Toaster />
      <div>
        <h2>Nuevo partido</h2>
      </div>
      <div>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="date">Fecha</label>
            <input
              type="datetime-local"
              name="date"
              id="date"
              onChange={handleChange}
              value={currentMatch.date}
            />
          </div>
          <div>
            <label htmlFor="competition">Competición</label>
            <select
              name="competition"
              value={currentMatch.competition?.competitionId || ""}
              onChange={(e) => {
                const selectedId = Number(e.target.value);
                const selected = competitions.find(
                  (c) => c.competitionId === selectedId
                );

                if (!selected) {
                  console.warn("Competición no encontrada");
                  return;
                }

                setCurrentMatch({
                  ...currentMatch,
                  competition: selected,
                });
              }}
            >
              <option value="">Selecciona la competición</option>
              {competitions.map((c) => (
                <option key={c.competitionId} value={c.competitionId}>
                  {c.name}
                </option>
              ))}
            </select>
            <label htmlFor="homeTeam">Equipo Local</label>
            <select
              name="homeTeam"
              value={currentMatch.homeTeam.teamId}
              onChange={(e) => {
                const selectedId = Number(e.target.value);
                const selected = teams.find((t) => t.teamId === selectedId);
                if (!selected) return;

                if (selected.teamId === currentMatch.awayTeam.teamId) {
                  toast.error(
                    "El equipo local no puede ser igual que el visitante"
                  );
                  return;
                }

                setCurrentMatch({
                  ...currentMatch,
                  homeTeam: selected,
                });
              }}
            >
              <option value={0}>Selecciona el equipo</option>
              {teams.map((team) => (
                <option key={team.teamId} value={team.teamId}>
                  {team.teamName}
                </option>
              ))}
            </select>
            <label htmlFor="awayTeam">Equipo Visitante</label>
            <select
              name="awayTeam"
              value={currentMatch.awayTeam.teamId}
              onChange={(e) => {
                const selectedId = Number(e.target.value);
                const selected = teams.find((t) => t.teamId === selectedId);
                if (!selected) {
                  console.log("No hay nada seleccionado" + selected);
                  return;
                }

                if (selected.teamId === currentMatch.homeTeam.teamId) {
                  toast.error(
                    "El equipo visitante no puede ser igual que el local"
                  );
                  return;
                }

                setCurrentMatch({
                  ...currentMatch,
                  awayTeam: selected,
                });
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
          <button type="submit">Crear partido</button>
        </form>
      </div>
    </div>
  );
};

export default MatchModal;
