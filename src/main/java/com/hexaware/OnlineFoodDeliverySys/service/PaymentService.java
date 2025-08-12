package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.PaymentDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Payment;

import java.util.List;

public interface PaymentService {
    Payment addPayment(PaymentDto dto);
    Payment updatePayment(Payment payment);
    Payment getByPaymentId(Long paymentId);
    String deleteByPaymentId(Long paymentId);
    List<Payment> getAllPayments();
    Payment getByBookingId(Long bookingId);
}
