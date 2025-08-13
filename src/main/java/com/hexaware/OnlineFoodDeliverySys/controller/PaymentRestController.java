package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.PaymentDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Payment;
import com.hexaware.OnlineFoodDeliverySys.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/payments")
public class PaymentRestController {
    @Autowired private PaymentService service;

    @PostMapping("/insert")
    public Payment add(@RequestBody @Valid PaymentDto dto) { return service.addPayment(dto); }

    @PutMapping("/update")
    public Payment update(@RequestBody @Valid Payment payment) { return service.updatePayment(payment); }

    @GetMapping("/getbyid/{paymentId}")
    public Payment get(@PathVariable Long paymentId) { return service.getByPaymentId(paymentId); }

    @DeleteMapping("/deletebyid/{paymentId}")
    public String delete(@PathVariable Long paymentId) { return service.deleteByPaymentId(paymentId); }

    @GetMapping("/getall")
    public List<Payment> all() { return service.getAllPayments(); }

    @GetMapping("/bybooking/{bookingId}")
    public Payment byBooking(@PathVariable Long bookingId) { return service.getByBooking(bookingId); }
}
