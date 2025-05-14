package com.example.pioner.dao;

import com.example.pioner.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("SELECT u FROM User u JOIN u.emails e WHERE e.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u JOIN u.phones p WHERE p.phone = :phone")
    User findByPhone(@Param("phone") String phone);

    @Query("SELECT u FROM User u " +
        "LEFT JOIN u.emails e " +
        "LEFT JOIN u.phones p " +
        "WHERE (:name IS NULL OR u.name LIKE CONCAT(:name, '%')) " +
        "AND (:dateOfBirth IS NULL OR u.dateOfBirth > :dateOfBirth) " +
        "AND (:phone IS NULL OR p.phone = :phone) " +
        "AND (:email IS NULL OR e.email = :email)")
    Page<User> searchUsers(
        @Param("name") String name,
        @Param("dateOfBirth") LocalDate dateOfBirth,
        @Param("phone") String phone,
        @Param("email") String email,
        Pageable pageable
    );
}