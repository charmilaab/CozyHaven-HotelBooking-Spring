import { useEffect, useState } from "react";
import { getAllHotels, addRoom, updateRoom, getRoomsByHotel, deleteRoom } from "../api/api";

const emptyRoom = {
  roomId: null,
  hotelId: "",
  roomType: "Single", // Updated to match backend validation
  baseFare: "",       // Changed from pricePerNight
  maxOccupancy: "",   // Changed from capacity
  bedType: "Single",  // Added required field
  size: ""           // Added required field
};

export default function AdminRooms() {
  const [hotels, setHotels] = useState([]);
  const [hotelId, setHotelId] = useState("");
  const [rooms, setRooms] = useState([]);
  const [form, setForm] = useState(emptyRoom);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  // Load hotels on component mount
  useEffect(() => {
    loadHotels();
  }, []);

  const loadHotels = async () => {
    try {
      console.log("Loading hotels...");
      const response = await getAllHotels();
      console.log("Hotels response:", response);
      
      if (response && response.data) {
        setHotels(response.data);
        console.log("Hotels loaded:", response.data);
      } else if (Array.isArray(response)) {
        setHotels(response);
        console.log("Hotels loaded (direct array):", response);
      } else {
        console.log("No hotels found");
        setHotels([]);
      }
    } catch (err) {
      console.error("Error loading hotels:", err);
      setError("Failed to load hotels. Please refresh the page.");
      setHotels([]);
    }
  };

  const loadRooms = async (hid) => {
    if (!hid) {
      setRooms([]);
      return;
    }
    
    try {
      setLoading(true);
      setError("");
      console.log("Loading rooms for hotel:", hid);
      
      const response = await getRoomsByHotel(hid);
      console.log("Rooms response:", response);
      
      if (response && response.data) {
        setRooms(response.data);
      } else if (Array.isArray(response)) {
        setRooms(response);
      } else {
        setRooms([]);
      }
    } catch (err) {
      console.error("Error loading rooms:", err);
      if (err.response && err.response.status === 500) {
        setError("API endpoint issue: Please check if your backend rooms API is running correctly");
      } else {
        setError("Failed to load rooms: " + (err.message || "Unknown error"));
      }
      setRooms([]);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async () => {
    setError("");
    setSuccess("");
    
    // Validation
    if (!form.hotelId) {
      setError("Please select a hotel");
      return;
    }
    if (!form.baseFare || Number(form.baseFare) <= 0) {
      setError("Please enter a valid base fare");
      return;
    }
    if (!form.maxOccupancy || Number(form.maxOccupancy) <= 0) {
      setError("Please enter a valid max occupancy");
      return;
    }
    if (!form.size || form.size.trim() === "") {
      setError("Please enter room size");
      return;
    }

    try {
      setLoading(true);
      
      const roomData = {
        hotelId: Number(form.hotelId),
        roomType: form.roomType,
        baseFare: Number(form.baseFare),
        maxOccupancy: Number(form.maxOccupancy),
        bedType: form.bedType,
        size: form.size.trim()
      };

      // Add roomId only for updates
      if (form.roomId) {
        roomData.roomId = form.roomId;
      }
      
      console.log("Submitting room data:", roomData);

      let response;
      if (form.roomId) {
        response = await updateRoom(roomData);
        setSuccess("Room updated successfully!");
      } else {
        response = await addRoom(roomData);
        setSuccess("Room added successfully!");
      }

      console.log("Submit response:", response);

      // Reset form but keep hotelId
      setForm({ ...emptyRoom, hotelId: form.hotelId });
      
      // Reload rooms
      await loadRooms(form.hotelId);
      
    } catch (err) {
      console.error("Error submitting room:", err);
      if (err.response && err.response.status === 500) {
        setError("API endpoint issue: Please check if your backend add/update room API is working");
      } else if (err.response && err.response.data) {
        // Show backend validation errors
        const errorMessage = typeof err.response.data === 'string' 
          ? err.response.data 
          : JSON.stringify(err.response.data);
        setError("Validation error: " + errorMessage);
      } else {
        setError((form.roomId ? "Failed to update room: " : "Failed to add room: ") + (err.message || "Unknown error"));
      }
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (room) => {
    console.log("Editing room:", room);
    setError("");
    setSuccess("");
    
    setForm({
      roomId: room.roomId,
      hotelId: String(room.hotel?.hotelId || hotelId),
      roomType: room.roomType || "Single",
      baseFare: String(room.baseFare || ""),
      maxOccupancy: String(room.maxOccupancy || ""),
      bedType: room.bedType || "Single",
      size: room.size || ""
    });
  };

  const handleDelete = async (roomId) => {
    if (!window.confirm("Are you sure you want to delete this room?")) {
      return;
    }
    
    try {
      setLoading(true);
      setError("");
      
      console.log("Deleting room:", roomId);
      await deleteRoom(roomId);
      setSuccess("Room deleted successfully!");
      
      // Reload rooms
      await loadRooms(hotelId);
      
    } catch (err) {
      console.error("Error deleting room:", err);
      if (err.response && err.response.status === 500) {
        setError("API endpoint issue: Please check if your backend delete room API is working");
      } else {
        setError("Failed to delete room: " + (err.message || "Unknown error"));
      }
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setForm({ ...emptyRoom, hotelId });
    setError("");
    setSuccess("");
  };

  const handleHotelChange = (selectedHotelId) => {
    console.log("Hotel changed to:", selectedHotelId);
    setHotelId(selectedHotelId);
    setForm({ ...emptyRoom, hotelId: selectedHotelId });
    setError("");
    setSuccess("");
    loadRooms(selectedHotelId);
  };

  return (
    <div className="max-w-6xl mx-auto p-6 bg-gray-50 min-h-screen">
      <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
        <h2 className="text-2xl font-bold text-gray-900 mb-6">Room Management</h2>
        
        {/* Hotel Selection */}
        <div className="mb-6">
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Select Hotel
          </label>
          <select
            value={hotelId}
            onChange={(e) => handleHotelChange(e.target.value)}
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white text-black"
            style={{
              color: '#000000',
              backgroundColor: '#ffffff'
            }}
          >
            <option value="" style={{ color: '#666666', backgroundColor: '#ffffff' }}>
              -- Select a hotel --
            </option>
            {hotels.map(hotel => (
              <option 
                key={hotel.hotelId || hotel.id} 
                value={hotel.hotelId || hotel.id}
                style={{ color: '#000000', backgroundColor: '#ffffff' }}
              >
                {hotel.hotelName || hotel.name}
              </option>
            ))}
          </select>
          
          {hotels.length === 0 && (
            <p className="text-sm text-gray-500 mt-1">
              No hotels available. Please add hotels first.
            </p>
          )}
        </div>

        {/* Success Message */}
        {success && (
          <div className="mb-4 p-3 bg-green-50 border border-green-200 rounded-md">
            <p className="text-green-600 text-sm">{success}</p>
          </div>
        )}

        {/* Error Message */}
        {error && (
          <div className="mb-4 p-3 bg-red-50 border border-red-200 rounded-md">
            <p className="text-red-600 text-sm">{error}</p>
          </div>
        )}

        {/* Room Form - Only show when hotel is selected */}
        {hotelId && (
          <div className="bg-gray-50 rounded-lg p-6 mb-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              {form.roomId ? "Edit Room" : "Add New Room"}
            </h3>
            
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mb-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Room Type *
                </label>
                <select
                  value={form.roomType}
                  onChange={(e) => setForm({ ...form, roomType: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white text-black"
                  style={{
                    color: '#000000',
                    backgroundColor: '#ffffff'
                  }}
                >
                  <option value="Single" style={{ color: '#000000', backgroundColor: '#ffffff' }}>Single</option>
                  <option value="Double" style={{ color: '#000000', backgroundColor: '#ffffff' }}>Double</option>
                  <option value="King" style={{ color: '#000000', backgroundColor: '#ffffff' }}>King</option>
                  <option value="Suite" style={{ color: '#000000', backgroundColor: '#ffffff' }}>Suite</option>
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Base Fare ($) *
                </label>
                <input
                  type="number"
                  min="0"
                  step="0.01"
                  placeholder="Enter base fare"
                  value={form.baseFare}
                  onChange={(e) => setForm({ ...form, baseFare: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Max Occupancy (guests) *
                </label>
                <input
                  type="number"
                  min="1"
                  max="10"
                  placeholder="Enter max occupancy"
                  value={form.maxOccupancy}
                  onChange={(e) => setForm({ ...form, maxOccupancy: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Bed Type *
                </label>
                <select
                  value={form.bedType}
                  onChange={(e) => setForm({ ...form, bedType: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white text-black"
                  style={{
                    color: '#000000',
                    backgroundColor: '#ffffff'
                  }}
                >
                  <option value="Single" style={{ color: '#000000', backgroundColor: '#ffffff' }}>Single</option>
                  <option value="Double" style={{ color: '#000000', backgroundColor: '#ffffff' }}>Double</option>
                  <option value="King" style={{ color: '#000000', backgroundColor: '#ffffff' }}>King</option>
                </select>
              </div>

              <div className="md:col-span-2">
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Room Size *
                </label>
                <input
                  type="text"
                  placeholder="e.g., 25 m²"
                  value={form.size}
                  onChange={(e) => setForm({ ...form, size: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  maxLength="30"
                />
              </div>
            </div>

            <div className="flex space-x-3">
              <button
                onClick={handleSubmit}
                disabled={loading}
                className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
              >
                {loading ? "Processing..." : (form.roomId ? "Update Room" : "Add Room")}
              </button>
              
              {form.roomId && (
                <button
                  onClick={handleCancel}
                  disabled={loading}
                  className="px-4 py-2 bg-gray-600 text-white rounded-md hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 transition-colors"
                >
                  Cancel
                </button>
              )}
            </div>
          </div>
        )}
      </div>

      {/* Rooms Table */}
      {hotelId && (
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
          <div className="px-6 py-4 border-b border-gray-200">
            <h3 className="text-lg font-semibold text-gray-900">
              Rooms ({rooms.length})
            </h3>
          </div>
          
          {loading ? (
            <div className="p-6 text-center">
              <div className="inline-block animate-spin rounded-full h-6 w-6 border-b-2 border-blue-600"></div>
              <p className="mt-2 text-gray-600">Loading rooms...</p>
            </div>
          ) : error && !rooms.length ? (
            <div className="p-6 text-center text-red-500">
              <div className="text-lg mb-2">Unable to load rooms</div>
              <div className="text-sm">Please check your API endpoints and try again.</div>
            </div>
          ) : !rooms.length ? (
            <div className="p-6 text-center text-gray-500">
              <div className="text-lg mb-2">No rooms found</div>
              <div className="text-sm">Add your first room using the form above.</div>
            </div>
          ) : (
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Room ID
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Type
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Base Fare
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Max Occupancy
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Bed Type
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Size
                    </th>
                    <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Actions
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {rooms.map((room) => (
                    <tr key={room.roomId || room.id} className="hover:bg-gray-50">
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        #{room.roomId || room.id}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                          room.roomType === 'Suite' ? 'bg-purple-100 text-purple-800' :
                          room.roomType === 'King' ? 'bg-blue-100 text-blue-800' :
                          room.roomType === 'Double' ? 'bg-green-100 text-green-800' :
                          'bg-gray-100 text-gray-800'
                        }`}>
                          {room.roomType}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        ${room.baseFare}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {room.maxOccupancy} guests
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {room.bedType}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {room.size}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
                        <button
                          onClick={() => handleEdit(room)}
                          disabled={loading}
                          className="text-blue-600 hover:text-blue-900 transition-colors disabled:opacity-50"
                        >
                          Edit
                        </button>
                        <button
                          onClick={() => handleDelete(room.roomId || room.id)}
                          disabled={loading}
                          className="text-red-600 hover:text-red-900 transition-colors disabled:opacity-50"
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}
    </div>
  );
}