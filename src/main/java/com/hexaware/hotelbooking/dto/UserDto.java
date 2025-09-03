package com.hexaware.hotelbooking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {
    
    private Long userId;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]{2,40}$", message = "Name should be alphabetic 2-40 chars")
    private String userName;

    @NotBlank @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!#%*?&])[A-Za-z\\d@$!#%*?&]{8,30}$",
            message = "Password must be 8-30 chars incl. upper/lower/digit/special")
    private String password;

    @NotBlank @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone must be 10 digits starting 6-9")
    private String phoneNumber;

    @NotBlank
    @Pattern(regexp = "^(ADMIN|HOTEL_OWNER|CUSTOMER)$", message = "Role must be ADMIN/HOTEL_OWNER/CUSTOMER")
    private String userRole;
}