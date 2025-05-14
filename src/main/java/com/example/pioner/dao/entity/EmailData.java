package com.example.pioner.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EMAIL_DATA")
public class EmailData
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String email;

    public User getUser()
    {
        return user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }
}
