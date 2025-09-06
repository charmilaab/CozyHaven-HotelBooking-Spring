import { useEffect, useState } from "react";
import {
  getAllHotels,
  getTransportByHotel,
  addTransport,
  updateTransport,
  deleteTransport,
} from "../api/api";

const empty = {
  transportId: null,
  hotelId: "",
  type: "Airport Pickup", // Changed from "mode"
  details: "",           // Changed from "provider"
  cost: 0,
  // Removed "notes" as it's not in backend
};

export default function AdminTransport() {
  const [hotels, setHotels] = useState([]);
  const [hotelId, setHotelId] = useState("");
  const [list, setList] = useState([]);
  const [form, setForm] = useState(empty);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  useEffect(() => {
    (async () => {
      try {
        setHotels((await getAllHotels()).data || []);
      } catch (e) {
        setError("Failed to load hotels");
      }
    })();
  }, []);

  const load = async (hid) => {
    if (!hid) return setList([]);
    try {
      setLoading(true);
      setList((await getTransportByHotel(hid)).data || []);
    } catch (e) {
      setError("Failed to load transport options");
    } finally {
      setLoading(false);
    }
  };

  const submit = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      const dto = {
        transportId: form.transportId || undefined,
        hotelId: Number(hotelId),
        type: form.type,        // Changed from "mode"
        details: form.details,  // Changed from "provider"
        cost: Number(form.cost),
      };

      if (form.transportId) {
        await updateTransport(dto);
        setSuccess("Transport updated successfully!");
      } else {
        await addTransport(dto);
        setSuccess("Transport added successfully!");
      }

      setForm({ ...empty, hotelId });
      await load(hotelId);
      setError("");
    } catch (e) {
      setError("Failed to save transport");
      setSuccess("");
    } finally {
      setLoading(false);
    }
  };

  const edit = (t) => {
    setForm({
      transportId: t.transportId,
      hotelId: String(t.hotelId || t.hotel?.hotelId || hotelId),
      type: t.type,           // Changed from "mode"
      details: t.details,     // Changed from "provider"
      cost: t.cost,
    });
    setSuccess("");
    setError("");
  };

  const remove = async (id) => {
    if (!window.confirm("Are you sure you want to delete this transport option?")) return;
    try {
      setLoading(true);
      await deleteTransport(id);
      await load(hotelId);
      setSuccess("Transport deleted successfully!");
    } catch (e) {
      setError("Failed to delete transport");
    } finally {
      setLoading(false);
    }
  };

  const getTypeIcon = (type) => {
    const icons = {
      
      "Cab": "🚕",
      "Bus": "🚌",
      "Bike": "🏍️",
    };
    return icons[type] || "🚗";
  };

  const getTypeColor = (type) => {
    const colors = {
      "Airport Pickup": "bg-blue-100 text-blue-800",
      "Cab": "bg-green-100 text-green-800", 
      "Bus": "bg-purple-100 text-purple-800",
    };
    return colors[type] || "bg-gray-100 text-gray-800";
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 via-teal-50 to-cyan-100 p-6">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-slate-800 mb-2">
            Transport Management
          </h1>
          <p className="text-slate-600">
            Manage transportation options for hotels
          </p>
        </div>

        {/* Alerts */}
        {error && (
          <div className="mb-6 bg-red-50 border-l-4 border-red-400 p-4 rounded-r-lg shadow-sm">
            <p className="text-sm text-red-700">{error}</p>
          </div>
        )}
        {success && (
          <div className="mb-6 bg-green-50 border-l-4 border-green-400 p-4 rounded-r-lg shadow-sm">
            <p className="text-sm text-green-700">{success}</p>
          </div>
        )}

        {/* Hotel Selector */}
        <div className="mb-6">
          <label className="block text-sm font-medium text-slate-700 mb-2">
            Select Hotel
          </label>
          <select
            className="w-full px-4 py-3 border border-slate-300 rounded-lg"
            value={hotelId}
            onChange={(e) => {
              setHotelId(e.target.value);
              load(e.target.value);
              setForm({ ...empty, hotelId: e.target.value });
            }}
          >
            <option value="">-- Select Hotel --</option>
            {hotels.map((h) => (
              <option key={h.hotelId} value={h.hotelId}>
                {h.name}
              </option>
            ))}
          </select>
        </div>

        {/* Transport Form */}
        {hotelId && (
          <form
            onSubmit={submit}
            className="bg-white rounded-xl shadow-lg border p-6 mb-8"
          >
            <h2 className="text-xl font-bold mb-4">
              {form.transportId ? "Edit Transport" : "Add Transport"}
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
              <div>
                <label className="block text-sm mb-2">Type</label>
                <select
                  name="type"
                  value={form.type}
                  onChange={(e) =>
                    setForm({ ...form, type: e.target.value })
                  }
                  className="w-full px-4 py-3 border rounded-lg"
                >
                  <option value="Airport Pickup">Airport Pickup</option>
                  <option value="Cab">Cab</option>
                  <option value="Bus">Bus</option>
                </select>
              </div>
              <div>
                <label className="block text-sm mb-2">Details</label>
                <input
                  type="text"
                  value={form.details}
                  onChange={(e) =>
                    setForm({ ...form, details: e.target.value })
                  }
                  className="w-full px-4 py-3 border rounded-lg"
                  placeholder="e.g., Mercedes Benz, AC Bus service"
                  required
                />
              </div>
              <div>
                <label className="block text-sm mb-2">Cost (₹)</label>
                <input
                  type="number"
                  value={form.cost}
                  onChange={(e) =>
                    setForm({ ...form, cost: e.target.value })
                  }
                  className="w-full px-4 py-3 border rounded-lg"
                  required
                />
              </div>
            </div>

            <div className="flex gap-4">
              <button
                type="submit"
                className="bg-teal-600 hover:bg-teal-700 text-white px-6 py-3 rounded-lg"
                disabled={loading}
              >
                {form.transportId ? "Update Transport" : "Add Transport"}
              </button>
              {form.transportId && (
                <button
                  type="button"
                  className="bg-gray-400 hover:bg-gray-500 text-white px-6 py-3 rounded-lg"
                  onClick={() => setForm({ ...empty, hotelId })}
                >
                  Cancel
                </button>
              )}
            </div>
          </form>
        )}

        {/* Transport List */}
        {hotelId && (
          <div>
            <h2 className="text-2xl font-bold mb-4">Transport Options</h2>
            {loading ? (
              <p>Loading...</p>
            ) : list.length === 0 ? (
              <p className="text-slate-600">No transport options found.</p>
            ) : (
              <div className="grid gap-4">
                {list.map((t) => (
                  <div
                    key={t.transportId}
                    className="bg-white rounded-xl shadow p-6 flex justify-between items-center"
                  >
                    <div>
                      <span
                        className={`inline-flex items-center px-3 py-1 rounded-full text-sm font-medium ${getTypeColor(
                          t.type
                        )} mr-2`}
                      >
                        {getTypeIcon(t.type)} {t.type}
                      </span>
                      <p className="font-bold">{t.details}</p>
                      <p className="text-slate-600">₹{t.cost}</p>
                    </div>
                    <div className="flex gap-2">
                      <button
                        onClick={() => edit(t)}
                        className="bg-amber-500 text-white px-4 py-2 rounded-lg"
                      >
                        Edit
                      </button>
                      <button
                        onClick={() => remove(t.transportId)}
                        className="bg-red-500 text-white px-4 py-2 rounded-lg"
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}