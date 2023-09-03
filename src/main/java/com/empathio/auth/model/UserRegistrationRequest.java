package com.empathio.auth.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @NotBlank
    @Size(min = 5, max = 20,
            message = "The username length must be in the range from 5 to 20")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d_.-]{5,}$",
            message = "The username length must be in the range from 5 to 20. (possible characters: *digits, *letters, *-, *., *_)")
    String username;

    @ExtendedEmailValidator
    String email;

    @NotBlank
    @Size(min = 5, max = 40,
            message = "The password length must be in the range from 5 to 40")
    @Pattern(regexp = "\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\"",
            message = "Password restriction: at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character")
    String password;

    @NotBlank
    String passwordAgain;

    boolean blocked;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{1}[A-z]+")
    @Size(min = 2, max = 60,
            message = "The first name length must be within bounds of 2 to 60 characters")
    String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{1}[A-z.'-]+")
    @Size(min = 2, max = 60,
            message = "The last name length must be within bounds of 2 to 60 characters")
    String lastName;

    @Min(value = 16, message = "You're too young to use our service")
    @Max(value = 90, message = "Your age is unreal")
    int age;
}
