import { Navigate } from "react-router-dom";
import { useAuthContext } from "../../context/AuthContext";

const PrivateAdminRoute = ({ children }: { children: React.ReactNode }) => {
  const { user, isAdmin } = useAuthContext();

  if (!user) {
    return <Navigate to={"/"} />;
  } else if (!isAdmin) {
    return <Navigate to={"/"} />;
  }

  return children;
};

export default PrivateAdminRoute;
