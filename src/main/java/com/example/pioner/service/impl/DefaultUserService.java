package com.example.pioner.service.impl;

import com.example.pioner.dao.EmailDataRepository;
import com.example.pioner.dao.PhoneDataRepository;
import com.example.pioner.dao.UserRepository;
import com.example.pioner.dao.entity.EmailData;
import com.example.pioner.dao.entity.PhoneData;
import com.example.pioner.dao.entity.User;
import com.example.pioner.exception.DuplicateEntityException;
import com.example.pioner.exception.EntityNotFoundException;
import com.example.pioner.exception.LastEntityRemovalException;
import com.example.pioner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class DefaultUserService implements UserService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailDataRepository emailDataRepository;

    @Autowired
    private PhoneDataRepository phoneDataRepository;

    public User findByEmail(final String email)
    {
        return userRepository.findByEmail(email);
    }

    public User findByPhone(final String phone)
    {
        return userRepository.findByPhone(phone);
    }

    public Page<User> searchUsers(final String name, final LocalDate dateOfBirth, final String phone, final String email, final Pageable pageable)
    {
        return userRepository.searchUsers(name, dateOfBirth, phone, email, pageable);
    }

    public Page<User> findAll(final Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public void addEmail(final Long userId, final String email)
    {
        final User user = findUserById(userId);
        validateEntityDoesNotExistEmail(email);

        EmailData emailData = new EmailData();
        emailData.setUser(user);
        emailData.setEmail(email);
        emailDataRepository.save(emailData);
    }

    @Transactional
    public void removeEmail(final Long userId, final String email)
    {
        final User user = findUserById(userId);
        validateUserHasMultipleEmailEntities(user);
        final EmailData emailData = findEmailDataByEmailAndUser(email, user);
        user.getEmails().remove(emailData);
        userRepository.save(user);
    }

    @Transactional
    public void updateEmail(final Long userId, final String oldEmail, final String newEmail)
    {
        final User user = findUserById(userId);
        validateEntityDoesNotExistEmail(newEmail);
        EmailData oldEmailData = findEmailDataByEmailAndUser(oldEmail, user);

        oldEmailData.setEmail(newEmail);
        emailDataRepository.save(oldEmailData);
    }

    @Transactional
    public void addPhone(final Long userId, final String phone)
    {
        final User user = findUserById(userId);
        validateEntityDoesNotExistPhone(phone);

        PhoneData phoneData = new PhoneData();
        phoneData.setUser(user);
        phoneData.setPhone(phone);
        phoneDataRepository.save(phoneData);
    }

    @Transactional
    public void removePhone(final Long userId, final String phone)
    {
        final User user = findUserById(userId);
        validateUserHasMultiplePhoneEntities(user);

        final PhoneData phoneData = findPhoneDataByPhoneAndUser(phone, user);
        user.getPhones().remove(phoneData);
        userRepository.save(user);
    }

    @Transactional
    public void updatePhone(final Long userId, final String oldPhone, final String newPhone)
    {
        final User user = findUserById(userId);
        validateEntityDoesNotExistPhone(newPhone);

        PhoneData oldPhoneData = findPhoneDataByPhoneAndUser(oldPhone, user);
        oldPhoneData.setPhone(newPhone);
        phoneDataRepository.save(oldPhoneData);
    }

    private User findUserById(final Long userId)
    {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    private EmailData findEmailDataByEmailAndUser(final String email, final User user)
    {
        final EmailData emailData = emailDataRepository.findByEmail(email);
        if (emailData == null || !emailData.getUser().getId().equals(user.getId()))
        {
            throw new EntityNotFoundException("Email not found or does not belong to the user: " + email);
        }
        return emailData;
    }

    private PhoneData findPhoneDataByPhoneAndUser(final String phone, final User user)
    {
        final PhoneData phoneData = phoneDataRepository.findByPhone(phone);
        if (phoneData == null || !phoneData.getUser().equals(user))
        {
            throw new EntityNotFoundException("Phone not found or does not belong to the user: " + phone);
        }
        return phoneData;
    }

    private void validateEntityDoesNotExistEmail(final String entityValue)
    {
        if (emailDataRepository.findByEmail(entityValue) != null)
        {
            throw new DuplicateEntityException("Email already exists: " + entityValue);
        }
    }

    private void validateEntityDoesNotExistPhone(final String entityValue)
    {
        if (phoneDataRepository.findByPhone(entityValue) != null)
        {
            throw new DuplicateEntityException("Phone already exists: " + entityValue);
        }
    }

    private void validateUserHasMultipleEmailEntities(final User user)
    {
        if (user.getEmails().size() <= 1)
        {
            throw new LastEntityRemovalException("Cannot remove the last email of the user");
        }
    }

    private void validateUserHasMultiplePhoneEntities(final User user)
    {
        if (user.getPhones().size() <= 1)
        {
            throw new LastEntityRemovalException("Cannot remove the last phone of the user");
        }
    }
}