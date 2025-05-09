import { NavLink } from "react-router-dom";
import "../navbar/Navbar.css";

const Navbar = () => {
  return (
    <>
      <div className="navbar-container">
        <img src="./src/assets/Logo.png" alt="logo" className="navbar-logo" />
        <div className="navbar-screens">
        <NavLink to="/" end className="navbar-link">Partidos</NavLink>
        <NavLink to="/store" className="navbar-link">Tienda</NavLink>
        <NavLink to="/predictions" className="navbar-link">Mis predicciones</NavLink>
          <div className="navbar-user">
            <p>Luis</p>
            <p> 100 </p>
          </div>
        </div>
      </div>
    </>
  );
};

export default Navbar;
