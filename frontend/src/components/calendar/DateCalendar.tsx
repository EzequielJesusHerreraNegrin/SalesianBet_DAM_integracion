// components/DateCalendar.tsx
import { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import CalendarTodayIcon from "@mui/icons-material/CalendarToday";
import "./DateCalendar.css";

interface DateCalendarProps {
  onSelectDate: (date: string) => void;
}

const DateCalendar = ({ onSelectDate }: DateCalendarProps) => {
  const [showCalendar, setShowCalendar] = useState(false);
  const [value, setValue] = useState<Date>(new Date());

  const handleDateChange = (date: Date | any) => {
    setValue(date);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const formattedDate = `${year}-${month}-${day}`;
    onSelectDate(formattedDate);
    setShowCalendar(false); // Cierra el calendario después de seleccionar
  };

  return (
    <div className="date-calendar-wrapper">
      <CalendarTodayIcon
        onClick={() => setShowCalendar(!showCalendar)}
        className="calendar-icon"
        style={{
          fontSize: "30px",
          marginLeft: "10px",
        }}
      />
      {showCalendar && (
        <div className="calendar-popup">
          <Calendar onChange={handleDateChange} value={value} />
        </div>
      )}
    </div>
  );
};

export default DateCalendar;
