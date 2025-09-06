package com.hexaware.hotelbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.hexaware.hotelbooking.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId")
    Payment findPaymentByBookingId(Long bookingId);
    
    @Query("SELECT p FROM Payment p WHERE p.booking.user.userId = :userId")
    List<Payment> findPaymentsByUserId(Long userId);
}
