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
        Payment p = new Payment();
        p.setPaymentId(dto.getPaymentId());
        p.setBooking(booking);
        p.setAmount(dto.getAmount());
        p.setPaymentMethod(dto.getPaymentMethod());
        p.setStatus(dto.getStatus());
        p.setPaymentDate(LocalDateTime.now());
        return repo.save(p);
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
