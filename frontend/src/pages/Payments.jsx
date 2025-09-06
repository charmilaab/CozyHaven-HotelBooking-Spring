import { useEffect, useState } from "react";
import { addPayment, getMyBookings } from "../api/api";
import { useAuth } from "../auth/AuthContext";

export default function Payments() {
  const { user } = useAuth();
  const [myBookings, setMyBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [form, setForm] = useState({
    bookingId: "",
    amount: "",
    paymentMethod: "UPI",
    status: "PAID"
  });
  const [msg, setMsg] = useState("");

  // Fetch user's bookings
  useEffect(() => {
    (async () => {
      if (!user?.userId) {
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        setError("");
        const bookings = await getMyBookings(user.userId);
        setMyBookings(bookings || []);
      } catch (err) {
        console.error("Error fetching bookings:", err);
        setError(err?.response?.data?.message || "Failed to fetch bookings");
      } finally {
        setLoading(false);
      }
    })();
  }, [user]);

  // Submit payment
  const submit = async (e) => {
    e.preventDefault();
    setMsg("");

    try {
      await addPayment({
        bookingId: Number(form.bookingId),
        amount: Number(form.amount),
        paymentMethod: form.paymentMethod,
        status: form.status
      });
setMsg("Payment saved.");
      // Update the booking state locally to reflect paid payment
      setMyBookings(prev =>
        prev.map(b =>
          b.bookingId === form.bookingId
            ? { ...b, payment: { status: form.status, amount: form.amount } }
            : b
        )
      );

      setMsg("Payment saved.");

      // Reset form
      setForm({
        bookingId: "",
        amount: "",
        paymentMethod: "UPI",
        status: "PAID"
      });
    } catch (e) {
      console.error("Payment error:", e);
      setMsg(e?.response?.data?.message || "Payment failed");
    }
  };

  return (
    <div className="max-w-2xl mx-auto mt-6 p-6 bg-white rounded-lg shadow-lg">
      <h2 className="text-3xl font-bold text-gray-800 mb-6 text-center border-b-2 border-green-500 pb-3">
        Payments
      </h2>

      <div className="space-y-6">
        {/* Booking Dropdown */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Select Booking:
          </label>
          <select
            value={form.bookingId}
            onChange={(e) => {
              const bookingId = Number(e.target.value);
              const booking = myBookings.find(b => b.bookingId === bookingId);
              setForm({
                ...form,
                bookingId,
                amount: booking?.totalAmount || ""
              });
            }}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500 bg-white"
            disabled={loading}
          >
            <option value="">
              {loading ? "Loading bookings..." : "-- choose --"}
            </option>
            {myBookings
              .filter(b => !b.payment || b.payment.status !== "PAID") // only unpaid
              .map(b => (
                <option key={b.bookingId} value={b.bookingId}>
                  #{b.bookingId} (Room {b.room?.roomId}) {b.checkInDate} → {b.checkOutDate}
                </option>
              ))}
          </select>

          {!loading && myBookings.length === 0 && !error && (
            <p className="mt-2 text-sm text-gray-600">
              No bookings found. Make sure you have created a booking first.
            </p>
          )}

          {error && (
            <p className="mt-2 text-sm text-red-600">
              {error}
            </p>
          )}
        </div>

        {/* Amount */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Amount
          </label>
          <input
            placeholder="Amount"
            value={form.amount}
            onChange={e => setForm({ ...form, amount: e.target.value })}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500 placeholder-gray-400"
          />
        </div>

        {/* Payment Method */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Payment Method:
          </label>
          <select
            value={form.paymentMethod}
            onChange={e => setForm({ ...form, paymentMethod: e.target.value })}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500 bg-white"
          >
            <option value="UPI">UPI</option>
            <option value="CARD">CARD</option>
            <option value="NETBANKING">NETBANKING</option>
            <option value="CASH">CASH</option>
          </select>
        </div>

        {/* Status */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Status:
          </label>
          <select
            value={form.status}
            onChange={e => setForm({ ...form, status: e.target.value })}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500 bg-white"
          >
            <option value="PAID">PAID</option>
            <option value="PENDING">PENDING</option>
            <option value="FAILED">FAILED</option>
            <option value="REFUNDED">REFUNDED</option>
          </select>
        </div>

        {/* Submit Button */}
        <button
          onClick={submit}
          disabled={loading || !form.bookingId}
          className="w-full bg-green-600 hover:bg-green-700 disabled:bg-gray-400 disabled:cursor-not-allowed text-white font-semibold py-3 px-4 rounded-md transition duration-200 ease-in-out transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2"
        >
          Save Payment
        </button>

        {/* Message */}
        {msg && (
          <div
            className={`px-4 py-3 rounded-md ${
              msg === "Payment saved."
                ? "bg-green-100 border border-green-400 text-green-700"
                : "bg-red-100 border border-red-400 text-red-700"
            }`}
          >
            {msg}
          </div>
        )}
      </div>
    </div>
  );
}
