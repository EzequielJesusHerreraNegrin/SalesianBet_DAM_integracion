import api from "./api";

const getAllUsers = async () => {
  const response = await api.get(`/users`);
  return response.data.data;
};

const getUserByEmail = async (email: string) => {
  const response = await api.get(`/users/email`, {
    params: {
      email,
    },
  });
  return response.data.data;
};

const UserService = {
  getAllUsers,
  getUserByEmail,
};

export default UserService;
