import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { createBooking, getRoomById } from "../api/api";
import { useEffect } from "react";
import { useAuth } from "../auth/AuthContext";

export default function Booking() {
  const { roomId } = useParams();
  const { user } = useAuth();
  const navigate = useNavigate();

  const [room, setRoom] = useState(null);
  const [checkInDate, setCheckInDate] = useState("");
  const [checkOutDate, setCheckOutDate] = useState("");
  const [numberOfRooms, setNumberOfRooms] = useState(1);
  const [err, setErr] = useState("");
  const [ok, setOk] = useState("");

  useEffect(() => {
    (async () => {
      try { 
        const roomData = await getRoomById(roomId);
        setRoom(roomData.data); 
      } catch (e) {
        console.error("Failed to fetch room:", e);
      }
    })();
  }, [roomId]);

  // Calculate total amount based on dates and room price
  const calculateTotal = () => {
    if (!room || !checkInDate || !checkOutDate) return 0;
    const checkIn = new Date(checkInDate);
    const checkOut = new Date(checkOutDate);
    const nights = Math.ceil((checkOut - checkIn) / (1000 * 60 * 60 * 24));
    return nights * room.baseFare * numberOfRooms;
  };

  const submit = async (e) => {
  e.preventDefault();
  setErr(""); setOk("");

  if (!user?.userId) {
    setErr("Please log in to make a booking");
    return;
  }

  try {
    const totalAmount = calculateTotal();

    const bookingData = {
      userId: user.userId,
      roomId: Number(roomId),
      checkInDate,
      checkOutDate,
      numberOfRooms,
      totalAmount,
      status: "CONFIRMED",
      // ✅ If user booked a transport earlier, pass transportId (optional)
      transportId: localStorage.getItem("selectedTransportId") 
        ? Number(localStorage.getItem("selectedTransportId"))
        : null,
    };

    const result = await createBooking(bookingData);
    setOk("Booking created successfully! Redirecting to payments...");
    setTimeout(() => navigate("/payments"), 1500);
  } catch (e) {
    console.error("Booking error:", e);
    setErr(e?.response?.data?.message || "Booking failed");
  }
};


  const totalAmount = calculateTotal();

  return (
    <div className="max-w-lg mx-auto mt-6 p-6 bg-white rounded-lg shadow-lg">
      <form onSubmit={submit} className="space-y-4">
        <h2 className="text-3xl font-bold text-gray-800 text-center border-b-2 border-blue-500 pb-2">
          Book Room #{roomId}
        </h2>
        
        {room && (
          <div className="bg-blue-50 p-4 rounded-lg border border-blue-200">
            <div><span className="font-semibold text-gray-700">Room Type:</span> {room.roomType}</div>
            <div><span className="font-semibold text-gray-700">Price per night:</span> ${room.baseFare}</div>
            {totalAmount > 0 && (
              <div className="mt-2 text-lg font-bold text-blue-600">
                Total Amount: ${totalAmount}
              </div>
            )}
          </div>
        )}
        
        <div className="space-y-2">
          <label className="block text-sm font-medium text-gray-700">Check-in Date</label>
          <input 
            type="date" 
            value={checkInDate} 
            onChange={e=>setCheckInDate(e.target.value)} 
            required 
            min={new Date().toISOString().split('T')[0]} // Prevent past dates
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
          />
        </div>
        
        <div className="space-y-2">
          <label className="block text-sm font-medium text-gray-700">Check-out Date</label>
          <input 
            type="date" 
            value={checkOutDate} 
            onChange={e=>setCheckOutDate(e.target.value)} 
            required 
            min={checkInDate || new Date().toISOString().split('T')[0]}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
          />
        </div>
        
        <div className="space-y-2">
          <label className="block text-sm font-medium text-gray-700">Number of Rooms</label>
          <input 
            type="number" 
            min={1} 
            value={numberOfRooms} 
            onChange={e=>setNumberOfRooms(Number(e.target.value))} 
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
          />
        </div>
        
        {err && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-md">
            {err}
          </div>
        )}
        
        {ok && (
          <div className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-md">
            {ok}
          </div>
        )}
        
        <button 
          type="submit" 
          disabled={!room || !checkInDate || !checkOutDate}
          className="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 disabled:cursor-not-allowed text-white font-semibold py-3 px-4 rounded-md transition duration-200 ease-in-out transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
        >
          Confirm Booking {totalAmount > 0 && `($${totalAmount})`}
        </button>
      </form>
    </div>
  );
}