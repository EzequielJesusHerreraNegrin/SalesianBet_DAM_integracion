import { useState } from "react";
import Schedulebar from "../components/Schedulebar/Schedulebar";
import Table from "../components/table/Table";

const MatchesPage = () => {
  const [selectedDate, setSelectedDate] = useState<string>("");

  const formatDate = (isoDate: string) => {
    if (!isoDate || typeof isoDate !== "string" || !isoDate.includes("T")) {
      return { date: "Fecha inválida", time: "Hora inválida" };
    }

    const [year, month, day] = isoDate.split("T")[0].split("-").map(Number);
    const date: Date = new Date(year, month - 1, day);

    const localDate = new Date(isoDate);

    const options: Intl.DateTimeFormatOptions = {
      weekday: "short",
      day: "numeric",
      month: "short",
    };

    const formatted = date.toLocaleDateString("es-ES", options);
    const time = localDate.toLocaleTimeString("es-ES", {
      hour: "2-digit",
      minute: "2-digit",
      hour12: false,
    });

    return {
      date: formatted.replace(".", ""),
      time,
    };
  };

  return (
    <>
      <Schedulebar setSelectedDate={setSelectedDate} formatDate={formatDate} />
      <Table selectedDate={selectedDate} formatDate={formatDate}/>
    </>
  );
};

export default MatchesPage;
