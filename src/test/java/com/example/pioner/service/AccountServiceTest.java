package com.example.pioner.service;

import com.example.pioner.dao.AccountRepository;
import com.example.pioner.dao.entity.Account;
import com.example.pioner.dao.entity.User;
import com.example.pioner.service.impl.DefaultAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceTest
{

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private DefaultAccountService accountService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTransferMoney_Success()
    {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);

        User fromUser = new User();
        fromUser.setId(fromUserId);

        User toUser = new User();
        toUser.setId(toUserId);

        Account fromAccount = new Account();
        fromAccount.setUser(fromUser);
        fromAccount.setBalance(BigDecimal.valueOf(500));

        Account toAccount = new Account();
        toAccount.setUser(toUser);
        toAccount.setBalance(BigDecimal.valueOf(300));

        when(accountRepository.findByUserId(fromUserId)).thenReturn(fromAccount);
        when(accountRepository.findByUserId(toUserId)).thenReturn(toAccount);

        accountService.transferMoney(fromUserId, toUserId, amount);

        // Assert
        assertEquals(BigDecimal.valueOf(400), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(400), toAccount.getBalance());
        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
    }

    @Test
    void testTransferMoney_AccountNotFound()
    {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);

        when(accountRepository.findByUserId(fromUserId)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.transferMoney(fromUserId, toUserId, amount);
        });

        assertEquals("Account not found", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void testTransferMoney_InsufficientBalance()
    {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal amount = BigDecimal.valueOf(1000);

        User fromUser = new User();
        fromUser.setId(fromUserId);

        User toUser = new User();
        toUser.setId(toUserId);

        Account fromAccount = new Account();
        fromAccount.setUser(fromUser);
        fromAccount.setBalance(BigDecimal.valueOf(500));

        Account toAccount = new Account();
        toAccount.setUser(toUser);
        toAccount.setBalance(BigDecimal.valueOf(300));

        when(accountRepository.findByUserId(fromUserId)).thenReturn(fromAccount);
        when(accountRepository.findByUserId(toUserId)).thenReturn(toAccount);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.transferMoney(fromUserId, toUserId, amount);
        });

        assertEquals("Insufficient balance", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }
}