package com.example.finalproject.dto.response.singIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private String name;
    private String email;
    private List<String> roles;
}
