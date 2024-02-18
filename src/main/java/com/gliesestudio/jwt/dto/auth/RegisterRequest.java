package com.gliesestudio.jwt.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Registration request payload with required details
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
@Data
public class RegisterRequest {

    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Firstname must contain only letters and spaces")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Lastname must contain only letters and spaces")
    private String lastName;

    @Size(min = 3, max = 20, message = "Username must be in the range of 3-20 character")
    @Pattern(regexp = "^[a-zA-Z0-9._-]*$", message = "Username may only contain letters, numbers, dots, underscores, and hyphens only")
    private String username;

    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one lowercase letter, uppercase letter, digit, one special character(@$!%*?&), and 8 characters long")
    private String password;

}