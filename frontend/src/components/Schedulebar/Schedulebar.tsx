import { Box, Tab, Tabs } from "@mui/material";
import { useEffect, useLayoutEffect, useRef, useState } from "react";

interface ScheduleProps {
  setSelectedDate: (date: string) => void;
  formatDate: (isoDate: string) => { date: string; time: string };
}

const Schedulebar = ({ setSelectedDate, formatDate }: ScheduleProps) => {
  const tabsUseRef = useRef<(HTMLDivElement | null)[]>([]);

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
    const response = filterDates[newValue].isoDate;
    setSelectedDate(filterDates[newValue].isoDate);
    console.log(response);
  };

  useEffect(() => {
    setSelectedDate(filterDates[value].isoDate);
  }, []);

  useLayoutEffect(() => {
    const tab = tabsUseRef.current[value]
    if (tab) {
      // Espera a que todo esté montado correctamente

      tab.scrollIntoView({
        behavior: "instant",
        block: "nearest",
        inline: "center",
      });
    }
  }, [value]);

  return (
    <div
      style={{ display: "flex", justifyContent: "center", marginTop: "30px" }}
    >
      <Box sx={{ maxWidth: "62.6%", display: "flex", color: "black" }}>
        <Tabs value={value} onChange={handleChange} variant="scrollable">
          {filterDates.map((day, index) => (
            <Tab
              key={index}
              label={day.label}
              sx={{
                border: "1px solid black",
                color: "black",
                backgroundColor: "#f08c00",
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
