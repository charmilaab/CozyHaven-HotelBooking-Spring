package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.entities.Payment;
import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment getPaymentById(Long paymentId);
    List<Payment> getAllPayments();
    Payment updatePayment(Long paymentId, Payment payment);
    void deletePayment(Long paymentId);
}
