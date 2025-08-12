package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.PaymentDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Payment;
import com.hexaware.OnlineFoodDeliverySys.service.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

    @Autowired
    private PaymentService service;

    @PostMapping("/insert")
    public Payment addPayment(@RequestBody @Valid PaymentDto dto) {
        log.info("Received request to add payment: {}", dto);
        return service.addPayment(dto);
    }

    @PutMapping("/update")
    public Payment updatePayment(@RequestBody @Valid Payment payment) {
        log.info("Received request to update payment: {}", payment);
        return service.updatePayment(payment);
    }

    @GetMapping("/getbyid/{paymentId}")
    public Payment getByPaymentId(@PathVariable Long paymentId) {
        log.info("Received request to fetch payment with ID: {}", paymentId);
        return service.getByPaymentId(paymentId);
    }

    @DeleteMapping("/deletebyid/{paymentId}")
    public String deleteByPaymentId(@PathVariable Long paymentId) {
        log.info("Received request to delete payment with ID: {}", paymentId);
        return service.deleteByPaymentId(paymentId);
    }

    @GetMapping("/getall")
    public List<Payment> getAllPayments() {
        log.info("Received request to fetch all payments");
        return service.getAllPayments();
    }
}
