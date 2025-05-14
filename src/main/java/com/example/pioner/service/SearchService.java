package com.example.pioner.service;

import com.example.pioner.dao.entity.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

/**
 * The SearchService interface defines methods for searching and retrieving user data.
 */
public interface SearchService
{

    /**
     * Searches for users based on the provided criteria and returns a paginated result.
     *
     * @param name        (optional) The name of the user to search for. If null or empty,
     *                    this criterion will be ignored.
     * @param dateOfBirth (optional) The date of birth of the user to search for. If null,
     *                    this criterion will be ignored.
     * @param phone       (optional) The phone number of the user to search for. If null or empty,
     *                    this criterion will be ignored.
     * @param email       (optional) The email address of the user to search for. If null or empty,
     *                    this criterion will be ignored.
     * @param page        The page number for pagination (0-based index).
     * @param size        The number of users to return per page.
     * @return A paginated list of users matching the search criteria.
     * @throws IllegalArgumentException if the page or size parameters are invalid.
     */
    Page<User> searchUsers(String name, LocalDate dateOfBirth, String phone, String email, int page, int size);

    /**
     * Retrieves all users in a paginated manner.
     *
     * @param page The page number for pagination (0-based index).
     * @param size The number of users to return per page.
     * @return A paginated list of all users.
     * @throws IllegalArgumentException if the page or size parameters are invalid.
     */
    Page<User> searchUsersAll(int page, int size);
}