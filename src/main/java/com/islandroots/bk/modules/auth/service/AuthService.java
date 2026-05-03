package com.islandroots.bk.modules.auth.service;

import com.islandroots.bk.modules.auth.dto.AuthResponse;
import com.islandroots.bk.modules.auth.dto.LoginRequest;
import com.islandroots.bk.modules.auth.dto.RegisterRequest;
import com.islandroots.bk.modules.user.entity.Role;
import com.islandroots.bk.modules.user.entity.User;
import com.islandroots.bk.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if user exists
        if (repository.findByEmailIgnoreCase(request.getEmail()).isPresent()) {
            throw new RuntimeException("Cet e-mail est déjà utilisé");
        }

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) // Par défaut ROLE_USER
                .createdAt(LocalDateTime.now())
                .build();
                
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
                
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthResponse refreshToken(String requestRefreshToken) {
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(token -> {
                    var user = token.getUser();
                    var jwtToken = jwtService.generateToken(user);
                    // On peut choisir de faire tourner le refresh token aussi
                    var newRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());
                    return AuthResponse.builder()
                            .token(jwtToken)
                            .refreshToken(newRefreshToken.getToken())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}
