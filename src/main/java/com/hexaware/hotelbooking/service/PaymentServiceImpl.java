package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.PaymentDto;
import com.hexaware.hotelbooking.entities.Booking;
import com.hexaware.hotelbooking.entities.Payment;
import com.hexaware.hotelbooking.exceptions.*;
import com.hexaware.hotelbooking.repository.BookingRepository;
import com.hexaware.hotelbooking.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired private PaymentRepository repo;
    @Autowired private BookingRepository bookingRepo;

    @Override
    public Payment addPayment(PaymentDto dto) {
        Booking booking = bookingRepo.findById(dto.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException("Booking not found: " + dto.getBookingId()));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(dto.getStatus());
        payment.setPaymentDate(LocalDateTime.now());

        // link both ways
        booking.setPayment(payment);
        booking.setStatus("PAID"); // ✅ update booking status

        bookingRepo.save(booking); // save booking with updated status
        return payment;
    }

    @Override
    public Payment updatePayment(Payment payment) {
        repo.findById(payment.getPaymentId())
            .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + payment.getPaymentId()));
        return repo.save(payment);
    }

    @Override
    public Payment getByPaymentId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + id));
    }
    @Override
    public List<Payment> getPaymentsByUser(Long userId) {
        return repo.findPaymentsByUserId(userId);
    }


    @Override
    public String deleteByPaymentId(Long id) {
        Payment p = getByPaymentId(id);
        repo.delete(p);
        return "Payment deleted successfully";
    }

    @Override
    public List<Payment> getAllPayments() { return repo.findAll(); }

    @Override
    public Payment getByBooking(Long bookingId) { return repo.findPaymentByBookingId(bookingId); }
}
