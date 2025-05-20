import toast from "react-hot-toast";
import { useAuthContext } from "../../../context/AuthContext";
import BetService from "../../../service/bet.service";
import { formatDate } from "../../../utils/uitls";
import "./PredictionTable.css";
import DeleteIcon from "@mui/icons-material/Delete";
const PredictionTable = () => {
  const { user } = useAuthContext();

  const onDeleteBet = async (betId: number) => {
    const data = await BetService.deleteBet(betId);
    if (!data) {
      toast.error("No se puede borrar esta predicción");
    } else {
      setTimeout(() => {
        toast.success("Se borró correctamente");
      });
    }
  };

  const renderHeader = () => {
    const headerElement = [
      "Fecha",
      "Partido",
      "Competición",
      "Puntos",
      "Predicción",
      "Acciones",
    ];

    return (
      <div className="prediction-row header">
        {headerElement.map((key, index) => {
          return (
            <div className="prediction-col" key={index}>
              {key.toUpperCase()}
            </div>
          );
        })}
      </div>
    );
  };

  const renderBody = () => {
    return (
      <div style={{ borderBottom: "1px solid black" }}>
        {user?.bets.map((bet) => (
          <div className="prediction-row" key={bet.betId}>
            <div className="prediction-col">{formatDate(bet.match.date)}</div>
            <div className="prediction-col">
              {bet.match.homeTeam.teamName +
                " - " +
                bet.match.awayTeam.teamName}
            </div>
            <div className="prediction-col">{bet.match.competition.name}</div>
            <div className="prediction-col">
              <span style={{ color: "green", fontWeight: "600" }}>
                {bet.points}
              </span>
            </div>
            <div className="prediction-col">{bet.prediction}</div>
            <button
              onClick={() => onDeleteBet(bet.betId)}
              className="prediction-col icon"
            >
              <DeleteIcon />
            </button>
          </div>
        ))}
      </div>
    );
  };

  return (
    <section className="prediction-container">
      <header>{renderHeader()}</header>
      {renderBody()}
    </section>
  );
};

export default PredictionTable;
