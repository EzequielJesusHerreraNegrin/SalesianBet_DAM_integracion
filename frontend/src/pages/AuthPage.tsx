const AuthPage = () => {
  return (
    <>
      <div>
        <form action="auth">
          <div>
            <img src="" alt="" />
          </div>
          <input type="file" name="profileImg" id="profileImg" />
          <div>
            <div>
              <label htmlFor="username">Nombre:</label>
              <input type="text" name="userName" id="userName" />
            </div>
            <div>
              <label htmlFor="email">Email:</label>
              <input type="email" name="email" id="email" />
            </div>
            <div>
              <label htmlFor="password">Contraseña:</label>
              <input type="password" name="password" id="password" />
            </div>
          </div>
          <div>
            <button>Enviar</button>
            <button>Cancelar</button>
          </div>
        </form>
      </div>
    </>
  );
};
