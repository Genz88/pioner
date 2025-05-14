package com.example.pioner.service;

/**
 * The AuthService interface defines methods for user authentication.
 */
public interface AuthService
{

    /**
     * Authenticates a user by their email or phone and password.
     *
     * @param nameOrPhone The email or phone number of the user to authenticate.
     * @param password    The password of the user to authenticate.
     * @return The ID of the authenticated user.
     * @throws RuntimeException if the credentials are invalid.
     */
    Long authenticate(String nameOrPhone, String password);
}
