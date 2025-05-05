import { useState } from "react";
import Navbar from "./components/navbar/Navbar";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import MatchesPage from "./pages/MatchesPage";
import MatchModal from "./components/modal/create/MatchModal";
import initialMatch, { Match } from "./type/Match";

function App() {
  const [currentMatch, setCurrentMatch] = useState<Match>(initialMatch);
  const [isCreating, setIsCreating] = useState<boolean>(true);
  const [matchesReady, setMatchesReady] = useState<boolean>(false);

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Navigate to="/matchesPage" replace />} />
        <Route
          path="/matchesPage"
          element={
            <MatchesPage
              setMatchesReady={setMatchesReady}
              matchesReady={matchesReady}
              setIsCreating={setIsCreating}
              setCurrentMatch={setCurrentMatch}
            />
          }
        />
        <Route
          path="/matchForm"
          element={
            <MatchModal
            matchesReady={matchesReady}
              isCreating={isCreating}
              currentMatch={currentMatch}
              setCurrentMatch={setCurrentMatch}
            />
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
