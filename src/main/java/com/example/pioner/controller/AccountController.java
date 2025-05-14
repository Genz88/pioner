package com.example.pioner.controller;

import com.example.pioner.service.impl.DefaultAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account Controller", description = "Endpoints for managing account operations")
public class AccountController
{

    @Autowired
    private DefaultAccountService accountService;

    @PostMapping("/transfer")
    @Operation(
        summary = "Transfer money between accounts",
        description = "Transfers a specified amount of money from the authenticated user's account to another user's account"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Transfer successful",
        content = @Content(mediaType = "text/plain")
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request parameters",
        content = @Content(mediaType = "text/plain")
    )
    @ApiResponse(
        responseCode = "401",
        description = "Unauthorized access",
        content = @Content(mediaType = "text/plain")
    )
    public String transferMoney(
        @RequestParam final Long toUserId,
        @RequestParam final BigDecimal amount,
        final Authentication authentication
    )
    {
        final Long fromUserId = Long.parseLong(authentication.getName());

        accountService.transferMoney(fromUserId, toUserId, amount);

        return "Translation completed successfully";
    }
}