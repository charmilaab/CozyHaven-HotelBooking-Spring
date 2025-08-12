package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.PaymentDto;
import com.hexaware.OnlineFoodDeliverySys.entities.Payment;
import com.hexaware.OnlineFoodDeliverySys.exceptions.PaymentNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository repo;

    @InjectMocks
    private PaymentServiceImpl service;

    private Payment payment;
    private PaymentDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment();
        payment.setPaymentId(401L);
        payment.setAmount(5000.0);
        payment.setPaymentMethod("CARD");
        payment.setStatus("PAID");
        payment.setPaymentDate(LocalDateTime.now());

        dto = new PaymentDto();
        dto.setPaymentId(401L);
        dto.setAmount(5000.0);
        dto.setPaymentMethod("CARD");
        dto.setStatus("PAID");
    }

    @Test
    void testAddPayment() {
        when(repo.save(any(Payment.class))).thenReturn(payment);
        Payment saved = service.addPayment(dto);
        assertEquals("CARD", saved.getPaymentMethod());
    }

    @Test
    void testGetByPaymentId_Found() {
        when(repo.findById(401L)).thenReturn(Optional.of(payment));
        assertEquals("CARD", service.getByPaymentId(401L).getPaymentMethod());
    }

    @Test
    void testGetByPaymentId_NotFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(PaymentNotFoundException.class, () -> service.getByPaymentId(999L));
    }

    @Test
    void testGetAllPayments() {
        when(repo.findAll()).thenReturn(Arrays.asList(payment));
        assertEquals(1, service.getAllPayments().size());
    }

    @Test
    void testDeleteByPaymentId() {
        when(repo.findById(401L)).thenReturn(Optional.of(payment));
        String result = service.deleteByPaymentId(401L);
        assertEquals("Payment deleted successfully", result);
    }
}
