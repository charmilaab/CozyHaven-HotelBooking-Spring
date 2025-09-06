import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getHotelById, getRoomsByHotel, getReviewsByHotel } from "../api/api";
import RoomPicker from "../components/RoomPicker.jsx";

export default function HotelDetails() {
  const { hotelId } = useParams();
  const [hotel, setHotel] = useState(null);
  const [rooms, setRooms] = useState([]);
  const [reviews, setReviews] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const h = (await getHotelById(hotelId)).data;
        setHotel(h);
        setRooms((await getRoomsByHotel(hotelId)).data || []);
        setReviews((await getReviewsByHotel(hotelId)).data || []);
      } catch (e) {
        console.error("Failed to load hotel details", e);
      }
    })();
  }, [hotelId]);

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <div className="bg-white shadow-sm border-b">
        <div className="max-w-4xl mx-auto px-6 py-4">
          <div className="flex items-center gap-4">
            <button
              onClick={() => navigate(-1)}
              className="text-blue-600 hover:text-blue-700 font-medium"
            >
              Back
            </button>
            <h1 className="text-2xl font-bold text-gray-800">{hotel?.name || "Hotel"}</h1>
          </div>
        </div>
      </div>

      {/* Content */}
      <div className="max-w-4xl mx-auto px-6 py-8">
        {/* Hotel Info */}
        <div className="bg-white rounded-lg shadow p-6 mb-6">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">{hotel?.name || "Hotel"}</h2>
          
          <div className="space-y-3">
            <div>
              <strong className="text-gray-700">Location:</strong>
              <span className="ml-2 text-gray-600">{hotel?.location || 'N/A'}</span>
            </div>
            
            <div>
              <strong className="text-gray-700">Description:</strong>
              <span className="ml-2 text-gray-600">{hotel?.description || 'No description available'}</span>
            </div>
            
            <div>
              <strong className="text-gray-700">Amenities:</strong>
              <span className="ml-2 text-gray-600">{hotel?.amenities || 'No amenities listed'}</span>
            </div>
          </div>
        </div>

        {/* Rooms Section */}
        <div className="bg-white rounded-lg shadow p-6 mb-6">
          <h2 className="text-xl font-bold text-gray-800 mb-4">Rooms</h2>
          <RoomPicker rooms={rooms} onPick={(roomId) => navigate(`/booking/${roomId}`)} />
        </div>

        {/* Reviews Section */}
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-bold text-gray-800 mb-4">Reviews</h2>
          
          {!reviews.length ? (
            <div className="text-gray-500">No reviews yet.</div>
          ) : (
            <div className="space-y-3">
              {reviews.map((r) => (
                <div key={r.reviewId} className="border-b border-gray-200 pb-3 last:border-b-0">
                  <div className="font-medium text-gray-700">{r.rating}/5</div>
                  <p className="text-gray-600 mt-1">{r.comment}</p>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}