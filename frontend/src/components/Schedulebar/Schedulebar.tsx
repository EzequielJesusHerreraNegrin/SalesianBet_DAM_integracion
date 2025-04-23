import { Box, Tab, Tabs } from "@mui/material";
import { useEffect, useRef, useState } from "react";

const Schedulebar = () => {
  const tabsUseRef = useRef<(HTMLDivElement | null)[]>([]);
  let selectedDate: string;
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

  const generateDates = () => {
    const dates = [];
    let date = new Date(2024, 7, 15, 0, 0);

    while (date.getFullYear() <= 2025) {
      if (date.getFullYear() >= 2024) {
        dates.push({
          label: formatDate(date.toISOString()).date,
          isoDate: date.toISOString().split("T")[0],
        });
      }
      date.setDate(date.getDate() + 1);
    }

    return dates;
  };

  const filterDates = generateDates();

  const today = new Date().toISOString().split("T")[0];
  const initialValue = filterDates.findIndex((d) => d.isoDate == today);
  const [value, setValue] = useState(initialValue >= 0 ? initialValue : 0);

  const handleChange = (_event: any, newValue: number) => {
    setValue(newValue);
  };

  useEffect(() => {
    setTimeout(() => {
      tabsUseRef.current[value]?.scrollIntoView({
        behavior: "smooth",
        block: "nearest",
        inline: "center",
      });
    }, 0);
  }, [value]);

  return (
    <div
      style={{ display: "flex", justifyContent: "center", marginTop: "30px" }}
    >
      <Box sx={{ maxWidth: "62.6%", display: "flex", color: "black"}}>
        <Tabs value={value} onChange={handleChange} variant="scrollable">
          {filterDates.map((day, index) => (
            <Tab
              key={index}
              label={ day.label }
              sx={{
                border: "1px solid black",
                color: "black",
                backgroundColor: "#f08c00"
              }}
              ref={(tab) => {
                tabsUseRef.current[index] = tab;
              }}
            />
          ))}
        </Tabs>
      </Box>
    </div>
  );
};

export default Schedulebar;
