package com.hexaware.OnlineFoodDeliverySys.service;

import java.util.List;
import com.hexaware.OnlineFoodDeliverySys.dto.PaymentDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Payment;

public interface PaymentService {
    Payment addPayment(PaymentDto dto);
    Payment updatePayment(Payment payment);
    Payment getByPaymentId(Long paymentId);
    String deleteByPaymentId(Long paymentId);
    List<Payment> getAllPayments();
    Payment getByBookingId(Long bookingId);
}
