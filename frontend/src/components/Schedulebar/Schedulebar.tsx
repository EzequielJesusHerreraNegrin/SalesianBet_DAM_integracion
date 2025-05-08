import { Box, Tab, Tabs } from "@mui/material";
import { useEffect, useLayoutEffect, useRef, useState } from "react";

interface ScheduleProps {
  setSelectedDate: (date: string) => void;
  formatDate: (isoDate: string) => { date: string; time: string };
  selectedDate: string;
}

const Schedulebar = ({
  selectedDate,
  setSelectedDate,
  formatDate,
}: ScheduleProps) => {
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

  const [value, setValue] = useState(() => {
    const index = filterDates.findIndex((d) => d.isoDate === selectedDate);
    return index >= 0 ? index : 0;
  });

  const handleChange = (_event: any, newValue: number) => {
    setValue(newValue);
    const response = filterDates[newValue].isoDate;
    setSelectedDate(filterDates[newValue].isoDate);
    console.log(response);
  };

  useEffect(() => {
    const index = filterDates.findIndex((d) => d.isoDate === selectedDate);
    if (index !== -1 && index !== value) {
      setValue(index);
    }
  }, [selectedDate]);

  useLayoutEffect(() => {
    const tab = tabsUseRef.current[value];
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
      <Box sx={{ maxWidth: "59%", display: "flex", color: "black" }}>
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
