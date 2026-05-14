package com.taskmanager.backend.controller;

import com.taskmanager.backend.dto.AuthResponse;
import com.taskmanager.backend.dto.LoginRequest;
import com.taskmanager.backend.dto.SignupRequest;
import com.taskmanager.backend.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody SignupRequest request,
            HttpServletResponse response
    ) {

        AuthResponse authResponse = authService.signup(request);

        Cookie cookie = new Cookie("jwt", authResponse.getToken());

        boolean isProduction =
                System.getenv("RAILWAY_ENVIRONMENT") != null;
        cookie.setHttpOnly(true);
        cookie.setSecure(isProduction);
        cookie.setPath("/");
        cookie.setMaxAge(7* 24 * 60 * 60);

        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {

        AuthResponse authResponse = authService.login(request);

        Cookie cookie = new Cookie("jwt", authResponse.getToken());

        boolean isProduction =
                System.getenv("RAILWAY_ENVIRONMENT") != null;
        cookie.setHttpOnly(true);
        cookie.setSecure(isProduction);
        cookie.setPath("/");
        cookie.setMaxAge(7* 24 * 60 * 60);

        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletResponse response
    ) {

        Cookie cookie = new Cookie("jwt", null);

        boolean isProduction =
                System.getenv("RAILWAY_ENVIRONMENT") != null;
        cookie.setHttpOnly(true);
        cookie.setSecure(isProduction);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}