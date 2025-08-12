package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    @Min(1)
    private Long userId;

    @Pattern(regexp = "^[A-Z][a-zA-Z]{1,29}$", message = "User name must start with a capital letter and contain only alphabets")
    private String userName;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,20}$",
             message = "Password must have at least one uppercase, one lowercase, one digit, and be 6-20 characters")
    private String password;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be a valid 10-digit Indian number")
    private String phoneNumber;

    @Pattern(regexp = "^(USER|ADMIN|OWNER)$", message = "User role must be USER, ADMIN, or OWNER")
    private String userRole;
}
