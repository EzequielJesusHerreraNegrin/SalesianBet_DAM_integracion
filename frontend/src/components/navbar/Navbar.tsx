import { NavLink, useNavigate } from "react-router-dom";
import "../navbar/Navbar.css";
import { useAuthContext } from "../../context/AuthContext";

const Navbar = () => {
  const { user, setIsLogin, logout, isAdmin } = useAuthContext();
  const navigate = useNavigate();

  const handleLogin = () => {
    setIsLogin(true);
    navigate("/login");
  };

  const handleRegister = () => {
    setIsLogin(false);
    navigate("/register");
  };

  return (
    <>
      {user ? (
        <div className="navbar-container">
          <div>
            <img
              src="./src/assets/Logo.png"
              alt="logo"
              className="navbar-logo"
            />
          </div>
          <div className="navbar-screens">
            <NavLink to="/" end className="navbar-link">
              Partidos
            </NavLink>
            <NavLink to="/store" className="navbar-link">
              Tienda
            </NavLink>
            {!isAdmin && (
              <NavLink to="/myPredictions" className="navbar-link">
                Mis predicciones
              </NavLink>
            )}
          </div>
          <div className="navbar-auth">
            <div className="navbar-user">
              <p> {user.userName} </p>
              <p> {user.points} </p>
            </div>
            <div>
              <button
                className="navbar-button"
                onClick={() => {
                  logout();
                  navigate("/");
                  window.location.reload();
                }}
              >
                Cerrar sesión
              </button>
            </div>
          </div>
        </div>
      ) : (
        <div className="navbar-container">
          <img src="./src/assets/Logo.png" alt="logo" className="navbar-logo" />
          <div className="navbar-screens">
            <NavLink to="/" end className="navbar-link">
              Partidos
            </NavLink>
            <NavLink to="/store" className="navbar-link">
              Tienda
            </NavLink>
          </div>
          <div className="navbar-auth">
            <button className="navbar-button" onClick={handleLogin}>
              Iniciar sesión
            </button>
            <button className="navbar-button" onClick={handleRegister}>
              Registrarse
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default Navbar;
