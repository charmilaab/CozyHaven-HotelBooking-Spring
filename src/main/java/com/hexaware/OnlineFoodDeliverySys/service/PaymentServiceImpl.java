package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.PaymentDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;
import com.hexaware.OnlineFoodDeliverySys.entities.Payment;
import com.hexaware.OnlineFoodDeliverySys.repository.BookingRepository;
import com.hexaware.OnlineFoodDeliverySys.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository repo;

    @Autowired
    private BookingRepository bookingRepo;

    @Override
    public Payment addPayment(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setPaymentId(dto.getPaymentId());
        Booking booking = bookingRepo.findById(dto.getBookingId()).orElse(null);
        payment.setBooking(booking);
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(dto.getStatus());
        payment.setPaymentDate(dto.getPaymentDate());
        return repo.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return repo.save(payment);
    }

    @Override
    public Payment getByPaymentId(Long paymentId) {
        return repo.findById(paymentId).orElse(null);
    }

    @Override
    public String deleteByPaymentId(Long paymentId) {
        repo.deleteById(paymentId);
        return "Payment deleted successfully";
    }

    @Override
    public List<Payment> getAllPayments() {
        return repo.findAll();
    }

    @Override
    public Payment getByBookingId(Long bookingId) {
        return repo.findPaymentByBookingId(bookingId);
    }
}
