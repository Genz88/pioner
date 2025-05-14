package com.example.pioner.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNT")
public class Account
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private BigDecimal initialDeposit;

    public User getUser()
    {
        return user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public void setBalance(final BigDecimal balance)
    {
        this.balance = balance;
    }

    public BigDecimal getInitialDeposit()
    {
        return initialDeposit;
    }

    public void setInitialDeposit(final BigDecimal initialDeposit)
    {
        this.initialDeposit = initialDeposit;
    }
}
