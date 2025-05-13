import { NavLink, useNavigate } from "react-router-dom";
import "../navbar/Navbar.css";
import { useAuthContext } from "../../context/AuthContext";

const Navbar = () => {
  const { user, setIsLogin } = useAuthContext();
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
          <img src="./src/assets/Logo.png" alt="logo" className="navbar-logo" />
          <div className="navbar-screens">
            <NavLink to="/" end className="navbar-link">
              Partidos
            </NavLink>
            <NavLink to="/store" className="navbar-link">
              Tienda
            </NavLink>
            <NavLink to="/predictions" className="navbar-link">
              Mis predicciones
            </NavLink>
            <div className="navbar-user">
              <p> {user.userName} </p>
              <p> {user.points} </p>
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
            <div className="navbar-auth">
              <button className="navbar-button" onClick={handleLogin}>
                Iniciar sesión
              </button>
              <button className="navbar-button" onClick={handleRegister}>
                Registrarse
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Navbar;
