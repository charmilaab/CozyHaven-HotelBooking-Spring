import { useEffect, useState } from "react";
import { useAuth } from "../auth/AuthContext";
import { getMyBookings } from "../api/api";

export default function MyBookings() {
  const { user } = useAuth();
  const [rows, setRows] = useState([]);

  useEffect(() => {
    (async () => {
      if (!user?.userId) return;
      const data = await getMyBookings(user.userId);
      setRows(data);
    })().catch(()=>{});
  }, [user]);

  return (
    <div className="max-w-4xl mx-auto p-6">
      <h2 className="text-2xl font-bold mb-6 text-gray-800">My Bookings</h2>
      
      {!rows.length ? (
        <div className="bg-gray-100 border border-gray-300 rounded-lg p-8 text-center">
          <p className="text-gray-600 text-lg">No bookings yet.</p>
        </div>
      ) : (
        <div className="overflow-x-auto shadow-lg rounded-lg">
          <table className="w-full bg-white border-collapse">
            <thead>
              <tr className="bg-blue-600 text-white">
                <th className="border border-blue-500 px-4 py-3 text-left font-semibold">ID</th>
                <th className="border border-blue-500 px-4 py-3 text-left font-semibold">Rooms</th>
                <th className="border border-blue-500 px-4 py-3 text-left font-semibold">Check-in</th>
                <th className="border border-blue-500 px-4 py-3 text-left font-semibold">Check-out</th>
                <th className="border border-blue-500 px-4 py-3 text-left font-semibold">Amount</th>
                <th className="border border-blue-500 px-4 py-3 text-left font-semibold">Status</th>
                <th className="border px-4 py-3 text-left font-semibold">Transport</th>

              </tr>
            </thead>
            <tbody>
  {rows.map((b, index) => (
    <tr 
      key={b.bookingId} 
      className={index % 2 === 0 ? "bg-gray-50" : "bg-white hover:bg-gray-100"}
    >
      <td className="border px-4 py-3">{b.bookingId}</td>
      <td className="border px-4 py-3">
        {b.numberOfRooms} Room{b.numberOfRooms > 1 ? "s" : ""}
      </td>
      <td className="border px-4 py-3">{b.checkInDate}</td>
      <td className="border px-4 py-3">{b.checkOutDate}</td>
      <td className="border px-4 py-3">${b.totalAmount?.toLocaleString() || "N/A"}</td>
      <td className="border px-4 py-3">
        <span className={`px-2 py-1 rounded-full text-xs font-medium ${
          b.status === "PAID"
            ? "bg-green-100 text-green-800"
            : b.status === "CONFIRMED"
            ? "bg-blue-100 text-blue-800"
            : b.status === "pending"
            ? "bg-yellow-100 text-yellow-800"
            : b.status === "cancelled"
            ? "bg-red-100 text-red-800"
            : "bg-gray-100 text-gray-800"
        }`}>
          {b.status}
        </span>
      </td>

      {/* ✅ Show transport if booked */}
      <td className="border px-4 py-3">
        {b.transport ? (
          <span className="text-sm text-blue-700 font-semibold">
            {b.transport.type} - ₹{b.transport.cost}
          </span>
        ) : (
          <span className="text-gray-400 text-sm">No Transport</span>
        )}
      </td>
    </tr>
  ))}
</tbody>

          </table>
        </div>
      )}
    </div>
  );
}