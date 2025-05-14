package com.example.pioner.service.impl;

import com.example.pioner.dao.UserRepository;
import com.example.pioner.dao.entity.User;
import com.example.pioner.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthService implements AuthService
{

    @Autowired
    private UserRepository userRepository;

    public Long authenticate(final String nameOrPhone, final String password)
    {
        final User user = userRepository.findByEmail(nameOrPhone);

        if (user != null && user.getPassword().equals(password))
        {
            return user.getId();
        }

        throw new RuntimeException("Invalid credentials");
    }
}
