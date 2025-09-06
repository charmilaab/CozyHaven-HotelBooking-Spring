import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export default function NavBar() {
  const { user, role, logout } = useAuth();
  const navigate = useNavigate();

  const linkStyle = ({ isActive }) => ({
    marginRight: "12px",
    textDecoration: isActive ? "underline" : "none",
    fontWeight: isActive ? "bold" : "normal",
  });

  return (
    <nav style={{ display: "flex", gap: 12, padding: 12, borderBottom: "1px solid #ddd" }}>
      <NavLink to="/" style={linkStyle}>
        Hotel Booking
      </NavLink>

      {/* CUSTOMER MENU */}
      {role === "CUSTOMER" && (
        <>
          <NavLink to="/my-bookings" style={linkStyle}>My Bookings</NavLink>
          <NavLink to="/payments" style={linkStyle}>Payments</NavLink>
          <NavLink to="/reviews" style={linkStyle}>Reviews</NavLink>
          <NavLink to="/transport" style={linkStyle}>Transport</NavLink>
        </>
      )}

      {/* ADMIN MENU */}
      {role === "ADMIN" && (
        <>
          <NavLink to="/admin/hotels" style={linkStyle}>Hotels</NavLink>
          <NavLink to="/admin/rooms" style={linkStyle}>Rooms</NavLink>
          <NavLink to="/admin/transport" style={linkStyle}>Transport</NavLink>
          <NavLink to="/admin/bookings" style={linkStyle}>Bookings</NavLink>
        </>
      )}

      <div style={{ marginLeft: "auto" }} />

      {/* AUTH LINKS */}
      {!user ? (
        <>
          <NavLink to="/login" style={linkStyle}>Login</NavLink>
          <NavLink to="/register" style={linkStyle}>Register</NavLink>
        </>
      ) : (
        <>
          <span>
            Hi, {user.userName} ({role})
          </span>
          <button
            style={{
              marginLeft: "12px",
              border: "none",
              background: "transparent",
              color: "blue",
              cursor: "pointer",
              textDecoration: "underline",
            }}
            onClick={() => {
              logout();
              navigate("/");
            }}
          >
            Logout
          </button>
        </>
      )}
    </nav>
  );
}
