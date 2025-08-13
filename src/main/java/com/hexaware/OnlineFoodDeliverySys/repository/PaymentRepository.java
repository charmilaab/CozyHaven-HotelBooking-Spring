package com.hexaware.OnlineFoodDeliverySys.repository;

import com.hexaware.OnlineFoodDeliverySys.entities.Payment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId")
    Payment findPaymentByBookingId(Long bookingId);
}
