package com.example.pioner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "User credentials for authentication")
public class LoginRequest
{
    @NotBlank(message = "Username cannot be blank")
    @Schema(description = "Username of the user", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "Password of the user", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(final String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }
}