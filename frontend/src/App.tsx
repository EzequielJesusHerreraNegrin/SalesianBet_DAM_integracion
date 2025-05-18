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
import initialMatch, { Match } from "./types/Match";
import StorePage from "./pages/store/StorePage";

function App() {
  const [currentMatch, setCurrentMatch] = useState<Match>(initialMatch);
  const [isCreating, setIsCreating] = useState<boolean>(true);
  const [matchesReady, setMatchesReady] = useState<boolean>(false);
  const [isBetting, setIsBetting] = useState<boolean>(false);

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Navigate to="/matchesPage" replace />} />
        <Route
          path="/matchesPage"
          element={
            <MatchesPage
              currentMatch={currentMatch}
              setMatchesReady={setMatchesReady}
              matchesReady={matchesReady}
              setIsCreating={setIsCreating}
              setCurrentMatch={setCurrentMatch}
              setIsBetting={setIsBetting}
              isBetting={isBetting}
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
        <Route path="/store" element={<StorePage />} />
      </Routes>
    </Router>
  );
}

export default App;
