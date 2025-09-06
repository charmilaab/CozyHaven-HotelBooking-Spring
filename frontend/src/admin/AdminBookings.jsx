import { useEffect, useState } from "react";
import {
  getAllBookings,
  updateBooking,
  deleteBooking,
} from "../api/api";

export default function AdminBookings() {
  const [bookings, setBookings] = useState([]);
  const [form, setForm] = useState({
    bookingDate: "",
    checkInDate: "",
    checkOutDate: "",
    status: "",
  });
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(true);

  // Load all bookings
  const loadBookings = async () => {
    try {
      setLoading(true);
      const res = await getAllBookings();
      setBookings(res.data || []);
    } catch (e) {
      console.error("❌ Failed to fetch bookings", e);
      setError("Failed to load bookings");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadBookings();
  }, []);

  // Handle input change
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Save booking (update only, no create here for admin)
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingId) {
        await updateBooking(editingId, form);
        setForm({
          bookingDate: "",
          checkInDate: "",
          checkOutDate: "",
          status: "",
        });
        setEditingId(null);
        setSuccess("Booking updated successfully ✅");
        loadBookings();
      }
    } catch (e) {
      console.error("❌ Failed to update booking", e);
      setError("Failed to update booking");
    }
  };

  // Delete booking
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this booking?")) return;
    try {
      await deleteBooking(id);
      setSuccess("Booking deleted successfully ❌");
      loadBookings();
    } catch (e) {
      console.error("❌ Failed to delete booking", e);
      setError("Failed to delete booking");
    }
  };

  // Edit booking
  const handleEdit = (booking) => {
    setForm({
      bookingDate: booking.bookingDate || "",
      checkInDate: booking.checkInDate || "",
      checkOutDate: booking.checkOutDate || "",
      status: booking.status || "",
    });
    setEditingId(booking.bookingId);
  };

  const getStatusColor = (status) => {
    const colors = {
      CONFIRMED: "bg-green-100 text-green-800",
      PENDING: "bg-yellow-100 text-yellow-800",
      CANCELLED: "bg-red-100 text-red-800",
      COMPLETED: "bg-blue-100 text-blue-800",
    };
    return colors[status] || "bg-gray-100 text-gray-800";
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 via-green-50 to-emerald-100 p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-slate-800 mb-2">
            Booking Management
          </h1>
          <p className="text-slate-600">
            Monitor and manage all hotel bookings
          </p>
        </div>

        {/* Error Alert */}
        {error && (
          <div className="mb-6 bg-red-50 border-l-4 border-red-400 p-4 rounded-r-lg shadow-sm">
            <p className="text-sm text-red-700">{error}</p>
          </div>
        )}

        {/* Success Alert */}
        {success && (
          <div className="mb-6 bg-green-50 border-l-4 border-green-400 p-4 rounded-r-lg shadow-sm">
            <p className="text-sm text-green-700">{success}</p>
          </div>
        )}

        {/* Edit Form */}
        {editingId && (
          <div className="bg-white rounded-2xl shadow-xl border border-slate-200 mb-8 overflow-hidden">
            <div className="bg-gradient-to-r from-green-600 to-emerald-600 px-6 py-4">
              <h2 className="text-xl font-semibold text-white">
                Edit Booking #{editingId}
              </h2>
            </div>

            <form onSubmit={handleSubmit} className="p-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-2">
                    Booking Date
                  </label>
                  <input
                    type="date"
                    name="bookingDate"
                    value={form.bookingDate}
                    onChange={handleChange}
                    className="w-full px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500 transition-colors"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-2">
                    Check-in Date
                  </label>
                  <input
                    type="date"
                    name="checkInDate"
                    value={form.checkInDate}
                    onChange={handleChange}
                    className="w-full px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500 transition-colors"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-2">
                    Check-out Date
                  </label>
                  <input
                    type="date"
                    name="checkOutDate"
                    value={form.checkOutDate}
                    onChange={handleChange}
                    className="w-full px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500 transition-colors"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-2">
                    Status
                  </label>
                  <select
                    name="status"
                    value={form.status}
                    onChange={handleChange}
                    className="w-full px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-green-500 transition-colors"
                    required
                  >
                    <option value="">Select Status</option>
                    <option value="PENDING">Pending</option>
                    <option value="CONFIRMED">Confirmed</option>
                    <option value="CANCELLED">Cancelled</option>
                    <option value="COMPLETED">Completed</option>
                  </select>
                </div>
              </div>

              <div className="flex gap-4">
                <button
                  type="submit"
                  className="bg-gradient-to-r from-green-600 to-emerald-600 text-white px-8 py-3 rounded-lg font-medium hover:from-green-700 hover:to-emerald-700 transition-all duration-200 shadow-lg hover:shadow-xl"
                >
                  Save Changes
                </button>
                <button
                  type="button"
                  onClick={() => {
                    setForm({
                      bookingDate: "",
                      checkInDate: "",
                      checkOutDate: "",
                      status: "",
                    });
                    setEditingId(null);
                  }}
                  className="bg-slate-500 text-white px-8 py-3 rounded-lg font-medium hover:bg-slate-600 transition-colors"
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        )}

        {/* Bookings List */}
        <div className="bg-white rounded-2xl shadow-xl border border-slate-200 overflow-hidden">
          <div className="bg-gradient-to-r from-green-600 to-emerald-600 px-6 py-4">
            <h2 className="text-xl font-semibold text-white">
              All Bookings ({bookings.length})
            </h2>
          </div>

          {loading ? (
            <p className="p-6 text-center text-slate-500">Loading...</p>
          ) : bookings.length === 0 ? (
            <div className="p-12 text-center">
              <p className="text-slate-600">No bookings found.</p>
            </div>
          ) : (
            <div className="divide-y divide-slate-200">
              {bookings.map((booking) => (
                <div
                  key={booking.bookingId}
                  className="p-6 hover:bg-slate-50 transition-colors"
                >
                  <div className="flex justify-between items-start">
                    <div className="flex-1">
                      <div className="flex items-center gap-4 mb-3">
                        <h3 className="text-lg font-semibold text-slate-800">
                          Booking #{booking.bookingId}
                        </h3>
                        <span
                          className={`px-3 py-1 rounded-full text-xs font-medium ${getStatusColor(
                            booking.status
                          )}`}
                        >
                          {booking.status}
                        </span>
                      </div>

                      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-sm">
                        <div className="text-slate-600">
                          Check-in: {booking.checkInDate}
                        </div>
                        <div className="text-slate-600">
                          Check-out: {booking.checkOutDate}
                        </div>
                        <div className="text-slate-600">
                          User: {booking.user?.userName || "N/A"}
                        </div>
                      </div>

                      <div className="mt-2 text-sm text-slate-500">
                        Hotel: {booking.hotel?.name || "N/A"}
                      </div>
                    </div>

                    {/* Action Buttons */}
                    <div className="flex gap-2 ml-6">
                      <button
                        onClick={() => handleEdit(booking)}
                        className="bg-amber-500 hover:bg-amber-600 text-white px-4 py-2 rounded-lg font-medium transition-colors"
                      >
                        Edit
                      </button>
                      <button
                        onClick={() => handleDelete(booking.bookingId)}
                        className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg font-medium transition-colors"
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
