package com.example.pioner.service.impl;

import com.example.pioner.dao.AccountRepository;
import com.example.pioner.dao.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class DefaultAccountService
{
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public synchronized void transferMoney(final Long fromUserId, final Long toUserId, final BigDecimal amount)
    {
        final Account fromAccount = accountRepository.findByUserId(fromUserId);
        final Account toAccount = accountRepository.findByUserId(toUserId);

        if (fromAccount == null || toAccount == null)
        {
            throw new RuntimeException("Account not found");
        }
        if (fromAccount.getBalance().compareTo(amount) < 0)
        {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void increaseBalance()
    {
        final Iterable<Account> accounts = accountRepository.findAll();
        for (Account account : accounts)
        {
            final BigDecimal initialDeposit = account.getInitialDeposit();
            final BigDecimal maxBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07));

            BigDecimal newBalance = account.getBalance().multiply(BigDecimal.valueOf(1.10));
            if (newBalance.compareTo(maxBalance) > 0)
            {
                newBalance = maxBalance;
            }

            account.setBalance(newBalance);
            accountRepository.save(account);
        }
    }
}
