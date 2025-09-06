import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function RequireRole({ allow }) {
  const { token, role } = useAuth();

  if (!token) return <Navigate to="/login" replace />;
  if (Array.isArray(allow) ? !allow.includes(role) : role !== allow)
    return <Navigate to="/" replace />;

  return <Outlet />;
}
