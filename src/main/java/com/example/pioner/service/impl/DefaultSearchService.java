package com.example.pioner.service.impl;

import com.example.pioner.dao.UserRepository;
import com.example.pioner.dao.entity.User;
import com.example.pioner.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DefaultSearchService implements SearchService
{
    @Autowired
    private UserRepository userRepository;

    public Page<User> searchUsers(final String name, final LocalDate dateOfBirth, final String phone, final String email, final int page, final int size)
    {
        final Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchUsers(name, dateOfBirth, phone, email, pageable);
    }

    public Page<User> searchUsersAll(final int page, final int size)
    {
        final Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }
}