package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.PaymentDto;
import com.hexaware.hotelbooking.entities.Payment;

import java.util.List;

public interface PaymentService {
    Payment addPayment(PaymentDto dto);
    Payment updatePayment(Payment payment);
    Payment getByPaymentId(Long id);
    String deleteByPaymentId(Long id);
    List<Payment> getAllPayments();
    Payment getByBooking(Long bookingId);
}
