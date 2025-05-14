package com.example.pioner.controller;

import com.example.pioner.dto.LoginRequest;
import com.example.pioner.security.JwtUtil;
import com.example.pioner.service.impl.DefaultAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "Endpoints for user authentication")
public class AuthController
{

    private final DefaultAuthService defaultAuthService;

    public AuthController(final DefaultAuthService defaultAuthService)
    {
        this.defaultAuthService = defaultAuthService;
    }

    @PostMapping("/login")
    @Operation(
        summary = "User login",
        description = "Authenticate user and generate JWT token if credentials are valid"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful login",
        content = @Content(mediaType = "application/json")
    )
    @ApiResponse(
        responseCode = "401",
        description = "Invalid credentials",
        content = @Content(mediaType = "text/plain")
    )
    public ResponseEntity<?> login(@RequestBody @Valid final LoginRequest request)
    {
        try
        {
            final Long userId = defaultAuthService.authenticate(request.getUsername(), request.getPassword());

            final String token = JwtUtil.generateToken(userId);

            return ResponseEntity.ok(Map.of("token", token));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
