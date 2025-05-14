package com.example.pioner.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailData> emails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneData> phones;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public List<EmailData> getEmails()
    {
        return emails;
    }

    public void setEmails(final List<EmailData> emails)
    {
        this.emails = emails;
    }

    public List<PhoneData> getPhones()
    {
        return phones;
    }

    public void setPhones(final List<PhoneData> phones)
    {
        this.phones = phones;
    }

    public Account getAccount()
    {
        return account;
    }

    public void setAccount(final Account account)
    {
        this.account = account;
    }

    public String getPassword()
    {
        return password;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }
}