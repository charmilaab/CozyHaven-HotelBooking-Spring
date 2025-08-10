package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentDto {

    private Long paymentId;

    private BookingDto booking;

    @Min(value = 0, message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    private String status;

    private LocalDateTime paymentDate;
}
