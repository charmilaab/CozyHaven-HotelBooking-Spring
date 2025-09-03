package com.hexaware.hotelbooking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PaymentDto {
    private Long paymentId;

    @NotNull @Positive
    private Long bookingId;

    @NotNull @Positive
    private Double amount;

    @NotBlank
    @Pattern(regexp = "^(CARD|UPI|NETBANKING|CASH)$", message = "paymentMethod must be CARD/UPI/NETBANKING/CASH")
    private String paymentMethod;

    @NotBlank
    @Pattern(regexp = "^(PENDING|PAID|FAILED|REFUNDED)$", message = "status must be PENDING/PAID/FAILED/REFUNDED")
    private String status;
}
