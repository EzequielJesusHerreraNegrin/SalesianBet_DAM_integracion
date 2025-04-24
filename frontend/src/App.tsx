import { useState } from "react";
import Navbar from "./components/navbar/Navbar";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import MatchesPage from "./pages/MatchesPage";

function App() {
  const [count, setCount] = useState(0);

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Navigate to="/matchesPage" replace/>} />
        <Route path="/matchesPage" element={<MatchesPage />} />
      </Routes>
    </Router>
  );
}

export default App;
