import { Routes, Route, Navigate } from "react-router-dom";
import NavBar from "./components/NavBar.jsx";
import Hotels from "./pages/Hotels.jsx";
import HotelDetails from "./pages/HotelDetails.jsx";
import Login from "./pages/Login.jsx";
import Register from "./pages/Register.jsx";
import Booking from "./pages/Booking.jsx";
import MyBookings from "./pages/MyBookings.jsx";
import Payments from "./pages/Payments.jsx";
import Reviews from "./pages/Reviews.jsx";
import Transport from "./pages/Transport.jsx";
import AdminHotels from "./admin/AdminHotels.jsx";
import AdminRooms from "./admin/AdminRooms.jsx";
import AdminTransport from "./admin/AdminTransport.jsx";
import AdminBookings from "./admin/AdminBookings.jsx";

import RequireRole from "./auth/RequireRole.jsx";

export default function App() {
  return (
    <>
      <NavBar />
      <Routes>
        {/* Public pages */}
        <Route path="/" element={<Hotels />} />
        <Route path="/hotel/:hotelId" element={<HotelDetails />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* CUSTOMER area */}
        <Route element={<RequireRole allow="CUSTOMER" />}>
          <Route path="/booking/:roomId" element={<Booking />} />
          <Route path="/my-bookings" element={<MyBookings />} />
          <Route path="/payments" element={<Payments />} />
          <Route path="/reviews" element={<Reviews />} />
          <Route path="/transport" element={<Transport />} />
        </Route>

        {/* ADMIN area */}
        <Route element={<RequireRole allow="ADMIN" />}>
          

          {/* Grouped admin routes */}
          <Route path="/admin/hotels" element={<AdminHotels />} />
          <Route path="/admin/rooms" element={<AdminRooms />} />
          <Route path="/admin/transport" element={<AdminTransport />} />
          <Route path="/admin/bookings" element={<AdminBookings />} />

        </Route>

        {/* Catch-all route */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </>
  );
}
