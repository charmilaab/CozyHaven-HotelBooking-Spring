import { useEffect, useState } from "react";
import { getAllHotels, getRoomsByHotel } from "../api/api";
import { useNavigate } from "react-router-dom";
import HotelCard from "../components/HotelCard.jsx";
import hotelBg from "../assets/hotel-bg.jpg"; 

export default function Hotels() {
  const [hotels, setHotels] = useState([]);
  const [err, setErr] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const res = await getAllHotels();
        setHotels(res.data || []);
      } catch (e) {
        console.error("❌ Error fetching hotels:", e);
        setErr("Failed to load hotels");
      }
    })();
  }, []);

  const openHotel = (hotel) => {
    navigate(`/hotel/${hotel.hotelId}`);
  };

  const quickBook = async (hotel) => {
    try {
      const { data: rooms } = await getRoomsByHotel(hotel.hotelId);
      if (!rooms?.length) {
        alert("No rooms available yet.");
        return;
      }
      navigate(`/booking/${rooms[0].roomId}`);
    } catch (e) {
      alert("Could not fetch rooms for this hotel.");
    }
  };

  return (
    <div
      style={{
        backgroundImage: `url(${hotelBg})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        minHeight: "100vh",
        padding: "40px 16px",
      }}
    >
      {/* ✅ No box wrapper */}
      <h2
        className="page-title"
        style={{ textAlign: "center", color: "#fff", textShadow: "1px 1px 3px rgba(0,0,0,0.7)" }}
      >
        All Hotels
      </h2>

      {err && <div className="alert alert-danger text-center">{err}</div>}

      {hotels.length === 0 && !err && (
        <div className="text-center" style={{ color: "#eee", fontWeight: "500" }}>
          No hotels found. Please add hotels from the admin dashboard.
        </div>
      )}

      <div
        style={{
          display: "grid",
          gap: "16px",
          gridTemplateColumns: "repeat(auto-fill, minmax(280px, 1fr))",
          marginTop: "20px",
        }}
      >
        {hotels.map((hotel) => (
          <HotelCard
            key={hotel.hotelId}
            hotel={hotel}
            onOpen={openHotel}
            onQuickBook={quickBook}
          />
        ))}
      </div>
    </div>
  );
}
