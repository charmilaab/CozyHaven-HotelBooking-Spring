import { useEffect, useState } from "react";
import { getAllHotels, addReview, getReviewsByHotel } from "../api/api";
import { useAuth } from "../auth/AuthContext";

export default function Reviews() {
  const { user } = useAuth();
  const [hotels, setHotels] = useState([]);
  const [hotelId, setHotelId] = useState("");
  const [text, setText] = useState("");
  const [rating, setRating] = useState(5);
  const [list, setList] = useState([]);

  useEffect(() => {
    (async () => {
      const result = await getAllHotels();
      setHotels(result.data || []);
    })();
  }, []);

  const load = async (hid) => {
    if (!hid) return setList([]);
    setList((await getReviewsByHotel(hid)).data || []);
  };

  const submit = async () => {
    if (!hotelId) return alert("Select a hotel");
    await addReview({
      hotelId: Number(hotelId),
      userId: user.userId,
      comment: text,
      rating: Number(rating)
    });
    setText("");
    await load(hotelId);
  };

  const getCleanHotelName = (hotel) => {
    if (!hotel) return "Unknown Hotel";
    let name = hotel.hotelName || hotel.name || `Hotel ${hotel.hotelId}`;
    return String(name).trim().replace(/[^\x20-\x7E]/g, '') || `Hotel ${hotel.hotelId}`;
  };

  const getHotelName = (id) => {
    const hotel = hotels.find(h => h.hotelId.toString() === id);
    return hotel ? getCleanHotelName(hotel) : "Unknown Hotel";
  };

  return (
    <div style={{ 
      maxWidth: '600px', 
      margin: '20px auto', 
      padding: '20px',
      backgroundColor: '#f9f9f9',
      borderRadius: '8px',
      boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
    }}>
      <h1 style={{ 
        color: '#333', 
        fontSize: '24px', 
        marginBottom: '20px',
        textAlign: 'center'
      }}>
        Hotel Reviews
      </h1>

      {/* Hotel Selection */}
      <div style={{ marginBottom: '20px' }}>
        <h3 style={{ color: '#555', fontSize: '16px', marginBottom: '10px' }}>
          Select Hotel:
        </h3>
        <div style={{ maxHeight: '150px', overflowY: 'auto' }}>
          {hotels.map(h => (
            <div 
              key={h.hotelId}
              onClick={() => {
                setHotelId(h.hotelId.toString());
                load(h.hotelId);
              }}
              style={{
                backgroundColor: hotelId === h.hotelId.toString() ? '#e3f2fd' : '#fff',
                border: hotelId === h.hotelId.toString() ? '2px solid #2196f3' : '1px solid #ddd',
                padding: '10px',
                margin: '3px 0',
                borderRadius: '4px',
                cursor: 'pointer',
                fontSize: '14px',
                color: '#333'
              }}
            >
              {getCleanHotelName(h)}
            </div>
          ))}
        </div>
      </div>

      {hotelId && (
        <div style={{ 
          backgroundColor: '#e8f5e8', 
          padding: '8px', 
          marginBottom: '15px',
          borderRadius: '4px',
          border: '1px solid #4caf50'
        }}>
          <span style={{ color: '#2e7d32', fontSize: '14px' }}>
            ✓ Selected: {getHotelName(hotelId)}
          </span>
        </div>
      )}

      {/* Review Form */}
      <div style={{ marginBottom: '15px' }}>
        <label style={{ color: '#555', fontSize: '14px', display: 'block', marginBottom: '5px' }}>
          Your Review:
        </label>
        <textarea 
          placeholder="Write your review..." 
          value={text} 
          onChange={e => setText(e.target.value)}
          style={{
            width: '100%',
            height: '80px',
            fontSize: '14px',
            padding: '8px',
            border: '1px solid #ddd',
            borderRadius: '4px',
            resize: 'vertical'
          }}
        />
      </div>

      <div style={{ marginBottom: '15px' }}>
        <label style={{ color: '#555', fontSize: '14px', display: 'block', marginBottom: '5px' }}>
          Rating:
        </label>
        <select
          value={rating}
          onChange={e => setRating(e.target.value)}
          style={{
            padding: '8px',
            fontSize: '14px',
            border: '1px solid #ddd',
            borderRadius: '4px',
            backgroundColor: '#fff'
          }}
        >
          <option value="1">1 Star</option>
          <option value="2">2 Stars</option>
          <option value="3">3 Stars</option>
          <option value="4">4 Stars</option>
          <option value="5">5 Stars</option>
        </select>
      </div>

      <button 
        onClick={submit}
        style={{
          width: '100%',
          padding: '10px',
          fontSize: '16px',
          backgroundColor: '#2196f3',
          color: 'white',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer',
          marginBottom: '20px'
        }}
      >
        Submit Review
      </button>

      {/* Reviews List */}
      {list.length > 0 && (
        <div>
          <h3 style={{ color: '#555', fontSize: '16px', marginBottom: '10px' }}>
            Reviews for {getHotelName(hotelId)}:
          </h3>
          {list.map(r => (
            <div 
              key={r.reviewId} 
              style={{
                backgroundColor: '#fff',
                padding: '12px',
                margin: '8px 0',
                border: '1px solid #eee',
                borderRadius: '4px'
              }}
            >
              <div style={{ marginBottom: '5px' }}>
                <span style={{ color: '#ff9800', fontSize: '14px' }}>
                  {'★'.repeat(r.rating)}{'☆'.repeat(5-r.rating)}
                </span>
                <span style={{ color: '#666', fontSize: '12px', marginLeft: '8px' }}>
                  {r.rating}/5
                </span>
              </div>
              <p style={{ color: '#333', fontSize: '14px', margin: 0, lineHeight: '1.4' }}>
                {r.comment}
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}