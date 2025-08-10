package com.hexaware.OnlineFoodDeliverySys.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDto {

    private Long userId;

    @Pattern(regexp = "[A-Z][a-z]{1,20}", message = "User name must start with a capital letter and have 2-21 letters")
    private String userName;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    private String userRole;

    private LocalDateTime createdAt;
}
