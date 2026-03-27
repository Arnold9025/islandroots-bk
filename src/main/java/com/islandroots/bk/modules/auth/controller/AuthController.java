package com.islandroots.bk.modules.auth.controller;

import com.islandroots.bk.modules.auth.dto.AuthResponse;
import com.islandroots.bk.modules.auth.dto.LoginRequest;
import com.islandroots.bk.modules.auth.dto.RegisterRequest;
import com.islandroots.bk.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}
