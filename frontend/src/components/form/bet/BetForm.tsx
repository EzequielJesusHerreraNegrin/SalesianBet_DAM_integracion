import { useState } from "react";
import { Match } from "../../../types/Match";
import toast, { Toaster } from "react-hot-toast";
import { BetRequest } from "../../../types/Bet";
import BetService from "../../../service/bet.service";
import "./BetForm.css";
import { useAuthContext } from "../../../context/AuthContext";

interface BetProps {
  currentMatch: Match;
  setIsBetting: (value: boolean) => void;
}

const BetForm = ({ currentMatch, setIsBetting }: BetProps) => {
  const [selected, setSelected] = useState<string>("");
  const [points, setPoints] = useState<number>(0);
  const { user, refreshUser } = useAuthContext();

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

  const predictionOptions = ["LOCAL", "EMPATE", "VISITANTE"];

  const handleClickOption = (option: string) => {
    setSelected(option);
  };

  const formatDate = (isoDate: string) => {
    const [year, month, day] = isoDate.split("T")[0].split("-");
    const dateFormatted = `${day}/${month}/${year}`;
    return dateFormatted;
  };

  const matchDate = formatDate(currentMatch.date);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!selected || !points || isNaN(points)) {
      toast.error("Completa todos los campos correctamente", {
        position: "top-right",
      });
      return;
    }

    try {
      const requestBet: BetRequest = {
        prediction: selected,
        points: points,
        matchId: currentMatch.matchId,
        userId: user!.userId,
      };
      await BetService.createBet(requestBet);
      refreshUser();
      toast.success("Creation of bet successfully", { position: "top-right" });

      setTimeout(() => {
        setIsBetting(false);
      }, 1000);
    } catch (error: any) {
      const message =
        error.response?.data?.message ||
        error.response?.data?.detail || // por si viene en otro campo
        error.message ||
        "Error al crear apuesta";

      console.error("Error al crear apuesta:", error); // muestra todo el objeto de error en consola
      toast.error(message, { position: "top-right" });
    }
  };

  console.log(currentMatch.competition.country.toLowerCase());
  return (
    <div className="bet-modal">
      <Toaster />
      <div className="modal-content bet-form">
        <div className="bet-header">
          <h2 className="bet-title">Predicción</h2>
        </div>
        <div className="bet-info">
          <div className="bet-label">
            <label>Competition:</label>
            <p style={{ display: "flex", alignItems: "center", gap: "10px" }}>
              <img
                src={`./src/assets/pais/${currentMatch.competition.country.toLowerCase()}.png`}
                alt="country"
                style={{ borderRadius: "50%", width: "25px", height: "25px" }}
              />
              {currentMatch.competition.name}
            </p>
          </div>
          <div className="bet-label">
            <label>Partido:</label>
            <p className="teams">
              <img src={teamHomeLogo} alt="homeTeam" />
              {currentMatch.homeTeam.teamName} -{" "}
              {currentMatch.awayTeam.teamName}
              <img src={teamAwayLogo} alt="awayTeam" />
            </p>
          </div>
          <div className="bet-label">
            <label>Fecha:</label>
            <p>{matchDate}</p>
          </div>
        </div>

        <div className="prediction-options">
          {predictionOptions.map((option, index) => (
            <div key={index} className="option-container">
              <div className="option-label">{option}</div>
              <div
                className={`option-box ${
                  selected === option ? "selected" : ""
                }`}
                onClick={() => handleClickOption(option)}
              >
                {selected === option ? "X" : ""}
              </div>
            </div>
          ))}
        </div>

        <div className="bet-container-inputs">
          <div className="bet-input">
            <label htmlFor="bet">Apuesta:</label>
            <input
              type="number"
              min={1}
              name="bet"
              id="bet"
              value={points}
              onChange={(e) => setPoints(Number(e.target.value))}
            />
          </div>
          <div className="bet-input">
            <label htmlFor="multiplier">Multiplicador</label>
            <p>X2</p>
          </div>
          <div className="bet-input">
            <label htmlFor="earnings">Ganancias</label>
            <p>{points * 2}</p>
          </div>
        </div>

        <div className="bet-buttons">
          <button onClick={handleSubmit} className="bet-button">
            Apostar
          </button>
          <button
            className="bet-button-cancel"
            onClick={() => setIsBetting(false)}
          >
            Cancelar
          </button>
        </div>
      </div>
    </div>
  );
};

export default BetForm;
