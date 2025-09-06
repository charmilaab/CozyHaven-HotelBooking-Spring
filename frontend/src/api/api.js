import axios from "axios";

// ✅ Hardcoded backend URL (no .env needed)
const api = axios.create({
  baseURL: "http://localhost:8080/api",
  withCredentials: false,
});

// ✅ Attach JWT on each request (only if token exists)
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// ✅ Logout on 401 Unauthorized
api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err?.response?.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("user");
      // window.location.href = "/login"; // enable if you want redirect
    }
    return Promise.reject(err);
  }
);

/* ============== AUTH ================== */
export const register = (data) =>
  api.post("/auth/register", {
    userName: data.userName,
    email: data.email,
    password: data.password,
    phoneNumber: data.phoneNumber ?? "9999999999",
    userRole: data.userRole ?? "CUSTOMER",
  });

export const login = (data) => api.post("/auth/login", data);

/* ============== USERS ================= */
export const getUserByEmail = (email) =>
  api.get(`/users/getbyemail/${encodeURIComponent(email)}`);
export const getAllUsers = () => api.get("/users/getall");
export const getUsersByRole = (role) =>
  api.get(`/users/getbyrole/${encodeURIComponent(role)}`);

/* ============== HOTELS ================ */
export const addHotel = (dto) => api.post("/hotels/insert", dto);
export const updateHotel = (id, hotel) =>
  api.put(`/hotels/update/${id}`, hotel);
export const deleteHotel = (id) => api.delete(`/hotels/delete/${id}`);
export const getAllHotels = () => api.get("/hotels/getall");
export const getHotelById = (id) => api.get(`/hotels/getbyid/${id}`);

/* ============== ROOMS ================= */
export const addRoom = (dto) => api.post("/rooms", dto); // Changed from /rooms/insert
export const updateRoom = (room) => api.put(`/rooms/${room.roomId}`, room); // Fixed - now uses roomId from data
export const getRoomById = (roomId) => api.get(`/rooms/${roomId}`); // Changed from /rooms/getbyid/
export const getRoomsByHotel = (hotelId) =>
  api.get(`/rooms/hotel/${hotelId}`); // Changed from /rooms/getbyhotel/
export const deleteRoom = (id) => api.delete(`/rooms/${id}`); // Changed from /rooms/delete/

/* ============== BOOKINGS ============== */
export const createBooking = (payload) =>
  api.post("/bookings/insert", payload);
export const updateBooking = (id, payload) =>
  api.put(`/bookings/update/${id}`, payload);
export const getAllBookings = async () => {
  const { data } = await api.get("/bookings/getall");
  return data; // just array
};

export const getBookingById = (id) => api.get(`/bookings/getbyid/${id}`);
export const deleteBooking = (id) => api.delete(`/bookings/delete/${id}`);
// ✅ Client-side filter for "My Bookings"
export const getMyBookings = (userId) =>
  api.get(`/bookings/getbyuser/${userId}`).then(res => res.data);


/* ============== PAYMENTS ============== */
export const addPayment = (dto) => api.post("/payments/insert", dto);
export const updatePayment = (id, payment) =>
  api.put(`/payments/update/${id}`, payment);
export const getPaymentById = (id) => api.get(`/payments/getbyid/${id}`);
export const deletePayment = (id) => api.delete(`/payments/delete/${id}`);
export const getAllPayments = () => api.get("/payments/getall");
export const getPaymentByBooking = (bookingId) =>
  api.get(`/payments/getbybooking/${bookingId}`);
/* ============== REVIEWS =============== */
export const addReview = (dto) => api.post("/reviews/insert", dto);
export const updateReview = (review) =>
  api.put("/reviews/update", review);
export const getReviewById = (id) => api.get(`/reviews/getbyid/${id}`);
export const deleteReview = (id) => api.delete(`/reviews/deletebyid/${id}`);
export const getAllReviews = () => api.get("/reviews/getall");
export const getReviewsByHotel = (hotelId) =>
  api.get(`/reviews/byhotel/${hotelId}`);
/* ============== TRANSPORT ============= */
export const addTransport = (dto) => api.post("/transport/insert", dto);
export const updateTransport = (transport) =>
  api.put(`/transport/update`, transport);
export const getTransportById = (id) => api.get(`/transport/getbyid/${id}`);
export const deleteTransport = (id) =>
  api.delete(`/transport/deletebyid/${id}`);
export const getAllTransport = () => api.get("/transport/getall");
export const getTransportByHotel = (hotelId) =>
  api.get(`/transport/getbyhotel/${hotelId}`);

export default api;
