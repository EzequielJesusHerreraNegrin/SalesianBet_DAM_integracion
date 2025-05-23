import { useState } from "react";
import {
  Navigate,
  Route,
  BrowserRouter as Router,
  Routes,
} from "react-router-dom";
import Navbar from "./components/navbar/Navbar";
import MatchesPage from "./pages/MatchesPage";
import initialMatch, { Match } from "./types/Match";
import AuthPage from "./pages/AuthPage";
import { useAuthContext } from "./context/AuthContext";
import MatchModal from "./components/form/create/MatchForm";
import PredictionsPage from "./pages/PredictionsPage";
import PrivateRoute from "./components/protection/PrivateRoute";
import HistoryPage from "./pages/HistoryPage";
import PrivateAdminRoute from "./components/protection/PrivateAdminRoute";
import StorePage from "./pages/store/StorePage";

function App() {
  const { isLogin } = useAuthContext();
  const [currentMatch, setCurrentMatch] = useState<Match>(initialMatch);
  const [isCreating, setIsCreating] = useState<boolean>(false);
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
              setIsCreating={setIsCreating}
            />
          }
        />
        <Route path="/store" element={<StorePage />} />
        <Route path={isLogin ? "/login" : "/register"} element={<AuthPage />} />

        <Route
          path="/myPredictions"
          element={
            <PrivateRoute>
              <PredictionsPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/history"
          element={
            <PrivateAdminRoute>
              <HistoryPage />
            </PrivateAdminRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
