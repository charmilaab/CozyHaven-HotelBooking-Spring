import { createContext, useContext, useState, useEffect } from "react";
import { login as apiLogin } from "../api/api.js";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(null);
  const [token, setToken] = useState(null);

  // 🔹 Restore session on page refresh
  useEffect(() => {
    const savedToken = localStorage.getItem("token");
    const savedUser = localStorage.getItem("user");
    const savedRole = localStorage.getItem("role");

    if (savedToken && savedUser && savedRole) {
      setToken(savedToken);
      setUser(JSON.parse(savedUser));
      setRole(savedRole);
    }
  }, []);

  // 🔹 Login
  const login = async (form) => {
    const { data } = await apiLogin(form);

    if (data.token) {
      localStorage.setItem("token", data.token);
      localStorage.setItem("role", data.role);
      localStorage.setItem("user", JSON.stringify(data.user));

      setToken(data.token);
      setRole(data.role);
      setUser(data.user);
    }

    return data;
  };

  // 🔹 Logout
  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("user");

    setToken(null);
    setRole(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, role, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

// Custom hook for easy usage
export const useAuth = () => useContext(AuthContext);
