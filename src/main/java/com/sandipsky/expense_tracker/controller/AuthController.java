package com.sandipsky.expense_tracker.controller;

import com.sandipsky.expense_tracker.entity.User;
import com.sandipsky.expense_tracker.service.AuthService;
import com.sandipsky.expense_tracker.dto.login.LoginRequest;
import com.sandipsky.expense_tracker.dto.login.LoginResponse;
import com.sandipsky.expense_tracker.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtUtil jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest user) {
        User authenticatedUser = authService.authenticate(user);

        String jwtToken = jwtUtils.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(authenticatedUser.getId(), authenticatedUser.getUsername(), authenticatedUser.getFullName(), jwtToken);
        return ResponseEntity.ok(loginResponse);
    }
}
