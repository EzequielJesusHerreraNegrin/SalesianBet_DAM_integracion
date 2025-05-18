import { useState } from "react";
import Schedulebar from "../components/Schedulebar/Schedulebar";
import Table from "../components/table/Table";
import { Match } from "../types/Match";
import BetForm from "../components/modal/bet/BetForm";

interface matchProp {
  setCurrentMatch: (match: Match) => void;
  setIsCreating: (value: boolean) => void;
  setMatchesReady: (value: boolean) => void;
  matchesReady: boolean;
  currentMatch: Match;
  setIsBetting: (value: boolean) => void;
  isBetting: boolean;
}

const MatchesPage = ({
  setIsCreating,
  setCurrentMatch,
  setMatchesReady,
  matchesReady,
  currentMatch,
  setIsBetting,
  isBetting,
}: matchProp) => {
  const today = new Date().toISOString().split("T")[0];
  const [selectedDate, setSelectedDate] = useState<string>(today);

  const formatDate = (isoDate: string) => {
    if (!isoDate || typeof isoDate !== "string" || !isoDate.includes("T")) {
      return { date: "Fecha inválida", time: "Hora inválida" };
    }

    const [year, month, day] = isoDate.split("T")[0].split("-").map(Number);
    const date: Date = new Date(year, month - 1, day);
    const today = new Date();

    const sameDay =
      date.getFullYear() == today.getFullYear() &&
      date.getMonth() == today.getMonth() &&
      date.getDate() == today.getDate();

    const localDate = new Date(isoDate);

    const optionsToday: Intl.DateTimeFormatOptions = {
      day: "numeric",
      month: "short",
    };

    const options: Intl.DateTimeFormatOptions = {
      weekday: "short",
      day: "numeric",
      month: "short",
    };

    const formatted = date.toLocaleDateString("es-ES", options);

    const formattedToday = date
      .toLocaleDateString("es-ES", optionsToday)
      .replace(".", "");

    const time = localDate.toLocaleTimeString("es-ES", {
      hour: "2-digit",
      minute: "2-digit",
      hour12: false,
    });

    return {
      date: sameDay
        ? `HOY ${formattedToday}`
        : formatted.replace(".", "").replace(",", ""),
      time,
    };
  };

  return (
    <>
      <Schedulebar
        selectedDate={selectedDate}
        setSelectedDate={setSelectedDate}
        formatDate={formatDate}
      />
      <Table
        setSelectedDate={setSelectedDate}
        setMatchesReady={setMatchesReady}
        matchesReady={matchesReady}
        setIsCreating={setIsCreating}
        selectedDate={selectedDate}
        formatDate={formatDate}
        setCurrentMatch={setCurrentMatch}
        setIsBetting={setIsBetting}
      />
      {isBetting && (
        <BetForm currentMatch={currentMatch} setIsBetting={setIsBetting} />
      )}
    </>
  );
};

export default MatchesPage;
