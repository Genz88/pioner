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
@Table(name = "PHONE_DATA")
public class PhoneData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String phone;

    public User getUser()
    {
        return user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(final String phone)
    {
        this.phone = phone;
    }

}