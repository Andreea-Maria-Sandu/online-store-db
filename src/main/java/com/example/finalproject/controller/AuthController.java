package com.example.finalproject.controller;

import com.example.finalproject.dto.request.user.AddUserRequest;
import com.example.finalproject.dto.request.user.SignInRequest;
import com.example.finalproject.dto.request.user.UserRequest;
import com.example.finalproject.dto.response.singIn.SignInResponse;
import com.example.finalproject.service.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/api/register")
    public ResponseEntity<Void> registerAdmin(@RequestBody @Valid AddUserRequest addUserRequest)
    {
        authService.getUserByEmail(addUserRequest.getEmail());
        authService.registerUser(addUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/api/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest)
    {
        SignInResponse response = authService.signIn(signInRequest);

        return ResponseEntity.ok().body(response);
    }


}
