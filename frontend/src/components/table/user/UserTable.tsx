import { useEffect, useState } from "react";
import { AuthenticatedUser } from "../../../type/User";
import UserService from "../../../service/user.service";
import VisibilityIcon from "@mui/icons-material/Visibility";
import "../Table.css";
import PredictionTable from "../prediction/PredictionTable";

const UserTable = () => {
  const [users, setUsers] = useState<AuthenticatedUser[]>([]);
  const [selectedUser, setSelectedUser] = useState<AuthenticatedUser | null>(
    null
  );
  const [showTable, setShowTable] = useState<boolean>(false);

  useEffect(() => {
    const fetchUsers = async () => {
      const data = await UserService.getAllUsers();
      setUsers(data);
    };
    fetchUsers();
  }, []);

  const handleViewUser = async (user: AuthenticatedUser) => {
    const userSelected = await UserService.getUserByEmail(user.email);
    console.log(userSelected);
    setSelectedUser(userSelected);
    setShowTable(true);
  };

  const renderHeader = () => {
    const headerElement = ["Nombre", "Email", "dni", "País", "Acciones"];

    return (
      <div className="row header">
        {headerElement.map((key, index) => {
          return (
            <div className="col" key={index}>
              {key.toUpperCase()}
            </div>
          );
        })}
      </div>
    );
  };

  const renderBody = () => {
    return (
      <div style={{ borderBottom: "1px solid black" }}>
        {users.map((user) => (
          <div className="row" key={user.userId}>
            <div className="col">{user.userName}</div>
            <div className="col">{user.email}</div>
            <div className="col">{user.dni}</div>
            <div className="col">
              <span style={{ color: "green", fontWeight: "600" }}>
                {user.country}
              </span>
            </div>
            <button onClick={() => handleViewUser(user)} className="col icon">
              <VisibilityIcon color="warning" />
            </button>
          </div>
        ))}
      </div>
    );
  };

  return (
    <>
      {showTable ? (
        <PredictionTable
          selectedUser={selectedUser}
          setSelectedUser={setSelectedUser}
          showTable={showTable}
          setShowTable={setShowTable}
        />
      ) : (
        <section className="container">
          <header>{renderHeader()}</header>
          {renderBody()}
        </section>
      )}
    </>
  );
};

export default UserTable;
