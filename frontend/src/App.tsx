import { useState } from "react";
import Navbar from "./components/navbar/Navbar";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import MatchesPage from "./pages/MatchesPage";
import initialMatch, { Match } from "./type/Match";
import AuthPage from "./pages/AuthPage";
import { useAuthContext } from "./context/AuthContext";
import MatchModal from "./components/form/create/MatchForm";
import PredictionsPage from "./pages/PredictionsPage";
import PrivateRoute from "./components/protection/PrivateRoute";

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
        <Route path={isLogin ? "/login" : "/register"} element={<AuthPage />} />

        <Route
          path={"/myPredictions"}
          element={
            <PrivateRoute>
              <PredictionsPage />
            </PrivateRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
