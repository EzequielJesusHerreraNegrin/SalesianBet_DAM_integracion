import { NavLink, useNavigate } from "react-router-dom";
import MenuIcon from "@mui/icons-material/Menu";
import CloseIcon from "@mui/icons-material/Close";
import SportsSoccerIcon from "@mui/icons-material/SportsSoccer";
import {
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemText,
  Divider,
  ListItemButton,
} from "@mui/material";

import "../navbar/Navbar.css";
import { useState } from "react";
import { useAuthContext } from "../../context/AuthContext";

const Navbar = () => {
  const { user, setIsLogin, logout, isAdmin } = useAuthContext();
  const navigate = useNavigate();
  const [drawerOpen, setDrawerOpen] = useState(false);

  const handleLogin = () => {
    setIsLogin(true);
    navigate("/login");
  };

  const handleRegister = () => {
    setIsLogin(false);
    navigate("/register");
  };

  const toggleDrawer = (open: boolean) => () => {
    setDrawerOpen(open);
  };

  const menuItems = user
    ? [
        { path: "/", label: "Partidos" },
        { path: "/store", label: "Tienda" },
        isAdmin
          ? { path: "/history", label: "Historial" }
          : { path: "/myPredictions", label: "Mis predicciones" },
      ]
    : [
        { path: "/", label: "Partidos" },
        { path: "/store", label: "Tienda" },
      ];

  const handleLogout = () => {
    logout();
    navigate("/");
    window.location.reload();
  };

  return (
    <>
      <div className="navbar-container">
        <div className="navbar-left">
          <img src="./src/assets/Logo.png" alt="logo" className="navbar-logo" />
          <h3>SalesiansBet</h3>
        </div>
        <div className="navbar-links-desktop">
          {menuItems.map((item, idx) => (
            <NavLink key={idx} to={item.path} className="navbar-link">
              {item.label}
            </NavLink>
          ))}
        </div>

        <div className="navbar-auth">
          {user ? (
            <>
              <div className="navbar-user">
                <p>{user.userName}</p>
                {!isAdmin && (
                  <p className="navbar-points">
                    {user.points} <SportsSoccerIcon />
                  </p>
                )}
              </div>
              <button className="navbar-button" onClick={handleLogout}>
                Cerrar sesión
              </button>
            </>
          ) : (
            <>
              <button className="navbar-button" onClick={handleLogin}>
                Iniciar sesión
              </button>
              <button className="navbar-button" onClick={handleRegister}>
                Registrarse
              </button>
            </>
          )}

          <IconButton
            className="navbar-menu-button"
            onClick={toggleDrawer(true)}
          >
            <MenuIcon />
          </IconButton>
        </div>
      </div>

      <Drawer anchor="right" open={drawerOpen} onClose={toggleDrawer(false)}>
        <div style={{ width: 250, padding: "1rem" }}>
          <IconButton onClick={toggleDrawer(false)}>
            <CloseIcon />
          </IconButton>
          <List>
            {menuItems.map((item, idx) => (
              <ListItem key={idx} disablePadding>
                <ListItemButton
                  onClick={() => {
                    navigate(item.path);
                    setDrawerOpen(false);
                  }}
                >
                  <ListItemText primary={item.label} />
                </ListItemButton>
              </ListItem>
            ))}
            <Divider />
            {user ? (
              <ListItem disablePadding>
                <ListItemButton onClick={handleLogout}>
                  <ListItemText primary="Cerrar sesión" />
                </ListItemButton>
              </ListItem>
            ) : (
              <>
                <ListItem disablePadding>
                  <ListItemButton onClick={handleLogin}>
                    <ListItemText primary="Iniciar sesión" />
                  </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                  <ListItemButton onClick={handleRegister}>
                    <ListItemText primary="Registrarse" />
                  </ListItemButton>
                </ListItem>
              </>
            )}
          </List>
        </div>
      </Drawer>
    </>
  );
};

export default Navbar;
