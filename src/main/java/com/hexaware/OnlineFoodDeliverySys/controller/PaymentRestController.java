package com.hexaware.OnlineFoodDeliverySys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.OnlineFoodDeliverySys.dto.PaymentDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Payment;
import com.hexaware.OnlineFoodDeliverySys.service.PaymentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

    @Autowired
    private PaymentService service;

    @PostMapping("/insert")
    public Payment addPayment(@RequestBody @Valid PaymentDto dto) {
        return service.addPayment(dto);
    }

    @PutMapping("/update")
    public Payment updatePayment(@RequestBody Payment payment) {
        return service.updatePayment(payment);
    }

    @GetMapping("/getbyid/{paymentId}")
    public Payment getByPaymentId(@PathVariable Long paymentId) {
        return service.getByPaymentId(paymentId);
    }

    @DeleteMapping("/deletebyid/{paymentId}")
    public String deleteByPaymentId(@PathVariable Long paymentId) {
        return service.deleteByPaymentId(paymentId);
    }

    @GetMapping("/getbybookingid/{bookingId}")
    public Payment getByBookingId(@PathVariable Long bookingId) {
        return service.getByBookingId(bookingId);
    }

    @GetMapping("/getall")
    public List<Payment> getAllPayments() {
        return service.getAllPayments();
    }
}
