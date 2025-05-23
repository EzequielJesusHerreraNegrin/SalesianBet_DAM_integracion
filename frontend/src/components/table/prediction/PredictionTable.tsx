import toast, { Toaster } from "react-hot-toast";
import { useAuthContext } from "../../../context/AuthContext";
import BetService from "../../../service/bet.service";
import { formatDate } from "../../../utils/uitls";
import "../Table.css";
import DeleteIcon from "@mui/icons-material/Delete";
import { useEffect, useState } from "react";
import { Bet } from "../../../type/Bet";
import { AuthenticatedUser } from "../../../type/User";
import SportsSoccerIcon from "@mui/icons-material/SportsSoccer";

interface PredictionProps {
  selectedUser?: AuthenticatedUser | null;
  setSelectedUser?: (user: AuthenticatedUser) => void;
  showTable?: boolean;
  setShowTable?: (value: boolean) => void;
}

const PredictionTable = ({
  selectedUser,
  showTable,
  setShowTable,
}: PredictionProps) => {
  const { user, refreshUser, isAdmin } = useAuthContext();
  const [bets, setBets] = useState<Bet[]>([]);

  useEffect(() => {
    const fetchUserBets = async () => {
      const userIdToUse =
        isAdmin && selectedUser ? selectedUser.userId : user?.userId;
      if (!userIdToUse) return;

      const data = await BetService.getBetsByUserId(userIdToUse);
      setBets(data);
    };
    fetchUserBets();
  }, [selectedUser, user]);

  const onDeleteBet = async (betId: number) => {
    try {
      await BetService.deleteBet(betId);
      setBets(bets.filter((bet) => bet.betId != betId));
      refreshUser();
    } catch (error: any) {
      toast.error("No puedes borrar esta predicción", {
        position: "top-right",
      });
    }
  };

  const renderHeader = () => {
    const headerElementAction = [
      "Fecha",
      "Partido",
      "Competición",
      "Puntos",
      "Predicción",
      "Acciones",
    ];

    const headerElement = [
      "Fecha",
      "Partido",
      "Competición",
      "Puntos",
      "Predicción",
    ];

    const headerToUse = isAdmin ? headerElement : headerElementAction;

    return (
      <div className="row header">
        {headerToUse.map((key, index) => {
          return (
            <div className="col" key={index}>
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
        {bets.map((bet) => (
          <div className="row" key={bet.betId}>
            <div className="col">{formatDate(bet.match.date)}</div>
            <div className="col">
              {bet.match.homeTeam.teamName +
                " - " +
                bet.match.awayTeam.teamName}
            </div>
            <div className="col">{bet.match.competition.name}</div>
            <div className="col">
              <span
                style={{
                  color: "green",
                  fontWeight: "600",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  justifyItems: "center",
                  gap: "5px",
                }}
              >
                {bet.points}{" "}
                <span style={{ display: "flex" }}>
                  <SportsSoccerIcon style={{ fontSize: "18px" }} />
                </span>
              </span>
            </div>
            <div className="col">{bet.prediction}</div>
            {!isAdmin && (
              <button
                onClick={() => onDeleteBet(bet.betId)}
                className="col icon"
              >
                <DeleteIcon />
              </button>
            )}
          </div>
        ))}
      </div>
    );
  };

  return (
    <>
      <Toaster />
      <section className="container">
        <header>{renderHeader()}</header>
        {renderBody()}

        {isAdmin && setShowTable && showTable && (
          <div className="button-container">
            <button className="button" onClick={() => setShowTable(false)}>
              Volver a usuarios
            </button>
          </div>
        )}
      </section>
    </>
  );
};

export default PredictionTable;
