package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentDto {

    @Min(1)
    private Long paymentId;

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double amount;

    @Pattern(regexp = "^(CASH|CARD|UPI)$", message = "Payment method must be CASH, CARD, or UPI")
    private String paymentMethod;

    @Pattern(regexp = "^(SUCCESS|FAILED|PENDING)$", message = "Status must be SUCCESS, FAILED, or PENDING")
    private String status;

    @PastOrPresent(message = "Payment date must be current or past")
    private LocalDateTime paymentDate;
}
