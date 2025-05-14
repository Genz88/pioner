package com.example.pioner.service;

import com.example.pioner.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * The UserService interface defines methods for managing user-related operations.
 */
public interface UserService {

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return The user associated with the email.
     */
    User findByEmail(String email);

    /**
     * Retrieves a user by their phone number.
     *
     * @param phone The phone number of the user to find.
     * @return The user associated with the phone number.
     */
    User findByPhone(String phone);

    /**
     * Searches for users based on the provided criteria.
     *
     * @param name        (optional) The name of the user to search for.
     * @param dateOfBirth (optional) The date of birth of the user to search for.
     * @param phone       (optional) The phone number of the user to search for.
     * @param email       (optional) The email address of the user to search for.
     * @param pageable    Pagination information.
     * @return A paginated list of users matching the search criteria.
     */
    Page<User> searchUsers(String name, LocalDate dateOfBirth, String phone, String email, Pageable pageable);

    /**
     * Retrieves all users in a paginated manner.
     *
     * @param pageable Pagination information.
     * @return A paginated list of all users.
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Adds an email address to a user.
     *
     * @param userId The ID of the user.
     * @param email  The email address to add.
     * @throws RuntimeException if the email already exists or the user is not found.
     */
    void addEmail(Long userId, String email);

    /**
     * Removes an email address from a user.
     *
     * @param userId The ID of the user.
     * @param email  The email address to remove.
     * @throws RuntimeException if the email does not exist or it is the last email of the user.
     */
    void removeEmail(Long userId, String email);

    /**
     * Updates an email address for a user.
     *
     * @param userId   The ID of the user.
     * @param oldEmail The current email address.
     * @param newEmail The new email address.
     * @throws RuntimeException if the new email already exists or the old email is not found.
     */
    void updateEmail(Long userId, String oldEmail, String newEmail);

    /**
     * Adds a phone number to a user.
     *
     * @param userId The ID of the user.
     * @param phone  The phone number to add.
     * @throws RuntimeException if the phone number already exists or the user is not found.
     */
    void addPhone(Long userId, String phone);

    /**
     * Removes a phone number from a user.
     *
     * @param userId The ID of the user.
     * @param phone  The phone number to remove.
     * @throws RuntimeException if the phone number does not exist or it is the last phone number of the user.
     */
    void removePhone(Long userId, String phone);

    /**
     * Updates a phone number for a user.
     *
     * @param userId   The ID of the user.
     * @param oldPhone The current phone number.
     * @param newPhone The new phone number.
     * @throws RuntimeException if the new phone number already exists or the old phone number is not found.
     */
    void updatePhone(Long userId, String oldPhone, String newPhone);
}
