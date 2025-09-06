import { useEffect, useState } from "react";
import { getAllHotels, getTransportByHotel } from "../api/api";

export default function Transport() {
  const [hotels, setHotels] = useState([]);
  const [hotelId, setHotelId] = useState("");
  const [list, setList] = useState([]);
  const [selectedTransport, setSelectedTransport] = useState(null);
  const [bookingConfirmed, setBookingConfirmed] = useState(false);

  useEffect(() => {
    (async () => {
      setHotels((await getAllHotels()).data || []);
    })();
  }, []);

  useEffect(() => {
    (async () => {
      if (!hotelId) return setList([]);
      setList((await getTransportByHotel(hotelId)).data || []);
      setSelectedTransport(null);
      setBookingConfirmed(false);
    })();
  }, [hotelId]);

  const handleSelectTransport = (transport) => {
    setSelectedTransport(transport);
    setBookingConfirmed(false);
  };

  const handleBookTransport = () => {
    if (selectedTransport) {
      // Here you would typically call an API to book the transport
      // For now, we'll just show a confirmation
      setBookingConfirmed(true);
      // You can add API call here: await bookTransport(selectedTransport.transportId);
    }
  };

  const getTypeIcon = (type) => {
    const icons = {
      "Airport Pickup": "✈️",
      "Cab": "🚕",
      "Bus": "🚌",
      "Bike": "🏍️",
    };
    return icons[type] || "🚗";
  };

  const getTypeColor = (type) => {
    const colors = {
      "Airport Pickup": "bg-blue-100 text-blue-800 border-blue-200",
      "Cab": "bg-green-100 text-green-800 border-green-200",
      "Bus": "bg-purple-100 text-purple-800 border-purple-200",
      "Bike": "bg-orange-100 text-orange-800 border-orange-200",
    };
    return colors[type] || "bg-gray-100 text-gray-800 border-gray-200";
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-100 p-6">
      <div className="max-w-4xl mx-auto">
        {/* Header */}
        <div className="mb-8 text-center">
          <h1 className="text-4xl font-bold text-slate-800 mb-2">
            Choose Your Transport
          </h1>
          <p className="text-slate-600">
            Select convenient transportation for your stay
          </p>
        </div>

        {/* Hotel Selector */}
        <div className="bg-white rounded-xl shadow-lg p-6 mb-6">
          <label className="block text-sm font-medium text-slate-700 mb-3">
            Select Your Hotel
          </label>
          <select
            className="w-full px-4 py-3 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            value={hotelId}
            onChange={(e) => setHotelId(e.target.value)}
          >
            <option value="">-- Choose Your Hotel --</option>
            {hotels.map((h) => (
              <option key={h.hotelId} value={h.hotelId}>
                {h.name}
              </option>
            ))}
          </select>
        </div>

        {/* Transport Options */}
        {hotelId && (
          <div className="mb-8">
            <h2 className="text-2xl font-bold text-slate-800 mb-6">
              Available Transport Options
            </h2>
            
            {!list.length ? (
              <div className="text-center py-12 bg-white rounded-xl shadow-lg">
                <div className="text-6xl mb-4">🚌</div>
                <p className="text-slate-500 text-lg">No transport options available</p>
                <p className="text-slate-400 text-sm mt-2">
                  Please try selecting a different hotel
                </p>
              </div>
            ) : (
              <div className="grid gap-4">
                {list.map((transport) => (
                  <div
                    key={transport.transportId}
                    className={`bg-white rounded-xl shadow-lg p-6 border-2 transition-all duration-200 cursor-pointer hover:shadow-xl ${
                      selectedTransport?.transportId === transport.transportId
                        ? "border-blue-500 bg-blue-50"
                        : "border-slate-200 hover:border-slate-300"
                    }`}
                    onClick={() => handleSelectTransport(transport)}
                  >
                    <div className="flex justify-between items-center">
                      <div className="flex items-center space-x-4">
                        <div className="text-3xl">
                          {getTypeIcon(transport.type)}
                        </div>
                        <div>
                          <div className="flex items-center space-x-3 mb-2">
                            <span
                              className={`inline-flex items-center px-3 py-1 rounded-full text-sm font-medium border ${getTypeColor(
                                transport.type
                              )}`}
                            >
                              {transport.type}
                            </span>
                            {selectedTransport?.transportId === transport.transportId && (
                              <span className="bg-blue-500 text-white px-2 py-1 rounded-full text-xs font-medium">
                                Selected
                              </span>
                            )}
                          </div>
                          <h3 className="text-lg font-semibold text-slate-800 mb-1">
                            {transport.details}
                          </h3>
                          <p className="text-slate-600">
                            Perfect for comfortable travel
                          </p>
                        </div>
                      </div>
                      <div className="text-right">
                        <div className="text-2xl font-bold text-green-600 mb-1">
                          ₹{transport.cost}
                        </div>
                        <p className="text-sm text-slate-500">per trip</p>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}

        {/* Booking Section */}
        {selectedTransport && (
          <div className="bg-white rounded-xl shadow-lg p-6">
            <h3 className="text-xl font-bold text-slate-800 mb-4">
              Booking Summary
            </h3>
            <div className="bg-slate-50 rounded-lg p-4 mb-6">
              <div className="flex justify-between items-center mb-2">
                <span className="text-slate-600">Selected Transport:</span>
                <span className="font-semibold">{selectedTransport.details}</span>
              </div>
              <div className="flex justify-between items-center mb-2">
                <span className="text-slate-600">Type:</span>
                <span className="font-semibold">{selectedTransport.type}</span>
              </div>
              <div className="flex justify-between items-center">
                <span className="text-slate-600">Total Cost:</span>
                <span className="text-xl font-bold text-green-600">
                  ₹{selectedTransport.cost}
                </span>
              </div>
            </div>

            {bookingConfirmed ? (
              <div className="text-center py-6">
                <div className="text-green-500 text-5xl mb-3">✅</div>
                <h4 className="text-xl font-bold text-green-700 mb-2">
                  Booking Confirmed!
                </h4>
                <p className="text-slate-600">
                  Your transport has been successfully booked. 
                  You will receive confirmation details shortly.
                </p>
              </div>
            ) : (
              <div className="flex gap-4 justify-center">
                <button
                  onClick={handleBookTransport}
                  className="bg-blue-600 hover:bg-blue-700 text-white px-8 py-3 rounded-lg font-semibold transition-colors duration-200"
                >
                  Book Transport
                </button>
                <button
                  onClick={() => setSelectedTransport(null)}
                  className="bg-gray-400 hover:bg-gray-500 text-white px-8 py-3 rounded-lg font-semibold transition-colors duration-200"
                >
                  Cancel
                </button>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}