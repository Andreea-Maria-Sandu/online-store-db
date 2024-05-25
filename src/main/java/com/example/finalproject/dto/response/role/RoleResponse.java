package com.example.finalproject.dto.response.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private String roleName;
    private List<String> users;
}
