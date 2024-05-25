package com.example.finalproject.dto.request.user;

import lombok.Data;

import javax.validation.constraints.Email;
import java.util.List;

@Data
public class AddUserRequest {

    private String username;
    private String password;
    @Email
    private String email;

    private List<String> rolesName;
}
