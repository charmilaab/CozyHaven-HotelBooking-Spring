package com.hexaware.hotelbooking.controller;

import com.hexaware.hotelbooking.dto.PaymentDto;
import com.hexaware.hotelbooking.entities.Payment;
import com.hexaware.hotelbooking.service.PaymentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173") 
public class PaymentRestController {
    @Autowired private PaymentService service;

    @PostMapping("/insert")
    public Payment add(@RequestBody @Valid PaymentDto dto) { return service.addPayment(dto); }

    @PutMapping("/update")
    public Payment update(@RequestBody @Valid Payment payment) { return service.updatePayment(payment); }

    @GetMapping("/getbyid/{paymentId}")
    public Payment get(@PathVariable Long paymentId) { return service.getByPaymentId(paymentId); }
    
    @GetMapping("/byuser/{userId}")
    public List<Payment> byUser(@PathVariable Long userId) {
        return service.getPaymentsByUser(userId);
    }


    @DeleteMapping("/deletebyid/{paymentId}")
    public String delete(@PathVariable Long paymentId) { return service.deleteByPaymentId(paymentId); }

    @GetMapping("/getall")
    public List<Payment> all() { return service.getAllPayments(); }

    @GetMapping("/bybooking/{bookingId}")
    public Payment byBooking(@PathVariable Long bookingId) { return service.getByBooking(bookingId); }
}
