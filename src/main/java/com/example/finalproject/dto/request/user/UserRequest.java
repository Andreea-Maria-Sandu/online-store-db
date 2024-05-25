package com.example.finalproject.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotEmpty
    private String name;
    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;
    @Pattern(regexp = "\\d{10}$") // regex pentru numarul de telefon ( 10 cifre)
    private String mobile;
    @Min(18)
    private Integer age;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @NotEmpty
    private String role;
}
