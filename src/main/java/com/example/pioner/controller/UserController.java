package com.example.pioner.controller;

import com.example.pioner.dao.entity.EmailData;
import com.example.pioner.dao.entity.PhoneData;
import com.example.pioner.service.impl.DefaultSearchService;
import com.example.pioner.service.impl.DefaultUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Operations related to users, emails, and phones")
public class UserController
{

    @Autowired
    private DefaultUserService defaultUserService;

    @Autowired
    private DefaultSearchService defaultSearchService;

    @GetMapping("/search")
    @Cacheable(value = "userSearch", key = "{#name, #dateOfBirth, #phone, #email, #page, #size}")
    @Operation(summary = "Search users", description = "Search users based on various parameters")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<?> searchUsers(
        @RequestParam(required = false) final String name,
        @RequestParam(required = false) final String dateOfBirth,
        @RequestParam(required = false) final String phone,
        @RequestParam(required = false) final String email,
        @RequestParam(defaultValue = "0") final int page,
        @RequestParam(defaultValue = "10") final int size)
    {

        return ResponseEntity.ok(defaultSearchService.searchUsers(name, dateOfBirth != null ? LocalDate.parse(dateOfBirth) : null, phone, email, page, size));
    }

    //todo delete
    @GetMapping("/searchandprint")
    @Operation(summary = "Search and print all users", description = "Retrieve all users with pagination")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<?> searchUsers(
        @RequestParam(defaultValue = "0") final int page,
        @RequestParam(defaultValue = "15") final int size)
    {
        return ResponseEntity.ok(defaultSearchService.searchUsersAll(page, size).getContent().stream()
            .map(u -> "Name:" + u.getName()
                + ", ID:" + u.getId()
                + ", Emails:" + u.getEmails().stream().map(EmailData::getEmail).collect(Collectors.toSet())
                + ", Phones:" + u.getPhones().stream().map(PhoneData::getPhone).collect(Collectors.toSet())
                + ", Balance:" + u.getAccount().getBalance()
                + ", InitialDeposit:" + u.getAccount().getInitialDeposit())
            .map(n -> n + ", ").collect(Collectors.toSet()));
    }

    @PostMapping("/{userId}/emails")
    @CacheEvict(value = "userSearch", allEntries = true)
    @Operation(summary = "Add email to user", description = "Add a new email to the specified user")
    @ApiResponse(responseCode = "200", description = "Email added successfully")
    public ResponseEntity<?> addEmail(@PathVariable final Long userId, @RequestParam final String email)
    {
        defaultUserService.addEmail(userId, email);
        return ResponseEntity.ok("Email added successfully");
    }

    @DeleteMapping("/{userId}/emails")
    @CacheEvict(value = "userSearch", allEntries = true)
    @Operation(summary = "Remove email from user", description = "Remove an email from the specified user")
    @ApiResponse(responseCode = "200", description = "Email removed successfully")
    public ResponseEntity<?> removeEmail(@PathVariable final Long userId, @RequestParam final String email)
    {
        defaultUserService.removeEmail(userId, email);
        return ResponseEntity.ok("Email removed successfully");
    }

    @PutMapping("/{userId}/emails")
    @CacheEvict(value = "userSearch", allEntries = true)
    @Operation(summary = "Update user email", description = "Update an existing email for the specified user")
    @ApiResponse(responseCode = "200", description = "Email updated successfully")
    public ResponseEntity<?> updateEmail(@PathVariable final Long userId, @RequestParam final String oldEmail, @RequestParam final String newEmail)
    {
        defaultUserService.updateEmail(userId, oldEmail, newEmail);
        return ResponseEntity.ok("Email updated successfully");
    }

    @PostMapping("/{userId}/phones")
    @CacheEvict(value = "userSearch", allEntries = true)
    @Operation(summary = "Add phone to user", description = "Add a new phone number to the specified user")
    @ApiResponse(responseCode = "200", description = "Phone added successfully")
    public ResponseEntity<?> addPhone(@PathVariable final Long userId, @RequestParam final String phone)
    {
        defaultUserService.addPhone(userId, phone);
        return ResponseEntity.ok("Phone added successfully");
    }

    @DeleteMapping("/{userId}/phones")
    @CacheEvict(value = "userSearch", allEntries = true)
    @Operation(summary = "Remove phone from user", description = "Remove a phone number from the specified user")
    @ApiResponse(responseCode = "200", description = "Phone removed successfully")
    public ResponseEntity<?> removePhone(@PathVariable final Long userId, @RequestParam final String phone)
    {
        defaultUserService.removePhone(userId, phone);
        return ResponseEntity.ok("Phone removed successfully");
    }

    @PutMapping("/{userId}/phones")
    @CacheEvict(value = "userSearch", allEntries = true)
    @Operation(summary = "Update user phone", description = "Update an existing phone number for the specified user")
    @ApiResponse(responseCode = "200", description = "Phone updated successfully")
    public ResponseEntity<?> updatePhone(@PathVariable final Long userId, @RequestParam final String oldPhone, @RequestParam final String newPhone)
    {
        defaultUserService.updatePhone(userId, oldPhone, newPhone);
        return ResponseEntity.ok("Phone updated successfully");
    }
}
