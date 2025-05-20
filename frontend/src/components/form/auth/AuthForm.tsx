import { useState } from "react";
import { useAuthContext } from "../../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import { initialUserRequest, UserRequest } from "../../../types/User";
import "./AuthForm.css";

const AuthForm = () => {
  const { loginUser, registerUser, isLogin, setIsLogin } = useAuthContext();
  const navigate = useNavigate();
  const [formData, setFormData] = useState<UserRequest>(initialUserRequest);

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (isLogin) {
      const token = await loginUser(formData);
      if (token) {
        setTimeout(() => {
          toast.success("Inicio de sesión exitoso");
          navigate("/");
        }, 1000);
      } else {
        toast.error("Error al iniciar sesión");
      }
    } else {
      const response = await registerUser(formData);
      if (response) {
        setTimeout(() => {
          toast.success("Registro exitoso");
          navigate("/login");
        });
      }
    }
  };

  return (
    <div className="auth-container">
      <h2 className="auth-title">
        {isLogin ? "Inicio de sesión" : "Registrarse"}
      </h2>
      <form onSubmit={handleSubmit} action="auth">
        <div className="auth-form">
          {!isLogin && (
            <div className="auth-input-container">
              <label htmlFor="username">Nombre:</label>
              <input
                type="text"
                name="userName"
                id="userName"
                value={formData.userName}
                onChange={handleOnChange}
              />
            </div>
          )}
          <div className="auth-input-container">
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              name="email"
              id="email"
              value={formData.email}
              onChange={handleOnChange}
            />
          </div>
          {!isLogin && (
            <div className="auth-input-container">
              <label htmlFor="dni">Dni:</label>
              <input
                type="text"
                name="dni"
                id="dni"
                value={formData.dni}
                onChange={handleOnChange}
              />
            </div>
          )}
          {!isLogin && (
            <div className="auth-input-container">
              <label htmlFor="country">País:</label>
              <input
                type="text"
                name="country"
                id="country"
                value={formData.country}
                onChange={handleOnChange}
              />
            </div>
          )}
          <div className="auth-input-container">
            <label htmlFor="password">Contraseña:</label>
            <input
              type="password"
              name="password"
              id="password"
              value={formData.password}
              onChange={handleOnChange}
            />
          </div>
        </div>
        <div style={{ display: "flex", justifyContent: "center" }}>
          <button
            className="auth-link"
            type="button"
            onClick={() => {
              const newValue = !isLogin;
              setIsLogin(newValue); // booleano ✅
              navigate(newValue ? "/login" : "/register"); // redirección según el valor
            }}
          >
            {isLogin
              ? "¿No tienes cuenta? Regístrate"
              : "¿Ya tienes cuenta? Inicia sesión"}
          </button>
        </div>
        <div className="auth-buttons">
          <button className="auth-button" type="submit">
            {isLogin ? "Iniciar sesión" : "Registrarse"}
          </button>
          <button onClick={() => navigate("/")} className="auth-button-cancel">
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
};

export default AuthForm;
