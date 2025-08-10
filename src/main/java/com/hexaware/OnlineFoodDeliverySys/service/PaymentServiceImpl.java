package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.PaymentDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Booking;
import com.hexaware.OnlineFoodDeliverySys.entities.Payment;
import com.hexaware.OnlineFoodDeliverySys.exceptions.PaymentNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.PaymentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Override
    public Payment addPayment(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setPaymentId(dto.getPaymentId());

        // Convert BookingDto → Booking entity
        Booking booking = new Booking();
        booking.setBookingId(dto.getBooking().getBookingId());
        payment.setBooking(booking);

        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(dto.getStatus());
        payment.setPaymentDate(dto.getPaymentDate());

        log.info("Adding new payment: {}", dto);
        return paymentRepo.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        if (!paymentRepo.existsById(payment.getPaymentId())) {
            throw new PaymentNotFoundException("Payment not found with ID: " + payment.getPaymentId());
        }
        return paymentRepo.save(payment);
    }

    @Override
    public Payment getByPaymentId(Long paymentId) {
        return paymentRepo.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + paymentId));
    }

    @Override
    public String deleteByPaymentId(Long paymentId) {
        if (!paymentRepo.existsById(paymentId)) {
            throw new PaymentNotFoundException("Cannot delete. Payment not found with ID: " + paymentId);
        }
        paymentRepo.deleteById(paymentId);
        return "Payment deleted successfully";
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    @Override
    public Payment getByBookingId(Long bookingId) {
        return paymentRepo.findPaymentByBookingId(bookingId);
    }
}
