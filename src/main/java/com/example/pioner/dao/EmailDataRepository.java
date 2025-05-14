package com.example.pioner.dao;

import com.example.pioner.dao.entity.EmailData;
import com.example.pioner.dao.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailData, Long>
{
    @Cacheable(value = "email", key = "#email")
    @Query("SELECT e FROM EmailData e WHERE e.email = :email")
    EmailData findByEmail(String email);

    @Cacheable(value = "userByEmail", key = "#email")
    @Query("SELECT e.user FROM EmailData e WHERE e.email = :email")
    User findUserByEmail(@Param("email") String email);
}
