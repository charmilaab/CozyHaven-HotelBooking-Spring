import { useEffect, useState } from "react";
import {
  getAllHotels,
  addHotel,
  updateHotel,
  deleteHotel,
} from "../api/api";

export default function AdminHotels() {
  const [hotels, setHotels] = useState([]);
  const [form, setForm] = useState({
    name: "",
    location: "",
    description: "",
    amenities: "",
  });
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const loadHotels = async () => {
    try {
      const res = await getAllHotels();
      setHotels(res.data || []);
    } catch (e) {
      console.error("❌ Failed to fetch hotels", e);
      setError("Failed to load hotels");
    }
  };

  useEffect(() => {
    loadHotels();
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingId) {
        await updateHotel(editingId, form);
        setSuccess("Hotel updated successfully!");
      } else {
        await addHotel(form);
        setSuccess("Hotel added successfully!");
      }

      setForm({ name: "", location: "", description: "", amenities: "" });
      setEditingId(null);
      loadHotels();
      setError("");
    } catch (e) {
      console.error("❌ Failed to save hotel", e);
      setError("Failed to save hotel");
      setSuccess("");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this hotel?")) return;
    try {
      await deleteHotel(id);
      loadHotels();
    } catch (e) {
      console.error("❌ Failed to delete hotel", e);
      setError("Failed to delete hotel");
    }
  };

  const handleEdit = (hotel) => {
    setForm({
      name: hotel.name,
      location: hotel.location,
      description: hotel.description,
      amenities: hotel.amenities,
    });
    setEditingId(hotel.hotelId);
    setSuccess("");
    setError("");
  };

  return (
    <div className="max-w-4xl mx-auto p-6">
      <h2 className="text-2xl font-bold mb-4 text-blue-600">
        Admin – Manage Hotels
      </h2>

      {error && <div className="bg-red-100 text-red-700 p-2 mb-4">{error}</div>}
      {success && (
        <div className="bg-green-100 text-green-700 p-2 mb-4">{success}</div>
      )}

      <form
        onSubmit={handleSubmit}
        className="bg-white shadow-md rounded-lg p-4 mb-6"
      >
        <div className="grid grid-cols-2 gap-4 mb-3">
          <input
            type="text"
            name="name"
            placeholder="Hotel Name"
            value={form.name}
            onChange={handleChange}
            className="border p-2 rounded"
            required
          />
          <input
            type="text"
            name="location"
            placeholder="Location"
            value={form.location}
            onChange={handleChange}
            className="border p-2 rounded"
            required
          />
        </div>
        <textarea
          name="description"
          placeholder="Description"
          value={form.description}
          onChange={handleChange}
          className="border p-2 rounded w-full mb-3"
          rows={2}
          required
        />
        <input
          type="text"
          name="amenities"
          placeholder="Amenities (comma separated)"
          value={form.amenities}
          onChange={handleChange}
          className="border p-2 rounded w-full mb-4"
        />

        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          {editingId ? "Update Hotel" : "Add Hotel"}
        </button>
        {editingId && (
          <button
            type="button"
            onClick={() => {
              setForm({
                name: "",
                location: "",
                description: "",
                amenities: "",
              });
              setEditingId(null);
            }}
            className="ml-2 bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-500"
          >
            Cancel
          </button>
        )}
      </form>

      {/* Hotel List */}
      <div className="space-y-4">
        {hotels.map((hotel) => (
          <div
            key={hotel.hotelId}
            className="bg-white shadow rounded-lg p-4 flex justify-between items-center"
          >
            <div>
              <h3 className="font-bold text-lg">{hotel.name}</h3>
              <p className="text-gray-600">📍 {hotel.location}</p>
              <p className="text-sm">{hotel.description}</p>
              <p className="text-sm text-gray-700">
                🏨 Amenities: {hotel.amenities}
              </p>
            </div>
            <div className="flex gap-2">
              <button
                onClick={() => handleEdit(hotel)}
                className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600"
              >
                Edit
              </button>
              <button
                onClick={() => handleDelete(hotel.hotelId)}
                className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700"
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
