package com.example.finalproject.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String name;
    private String email;
    private String mobile;
    private Integer age;
    private List<String> products;
    private List<String> roles;
}
