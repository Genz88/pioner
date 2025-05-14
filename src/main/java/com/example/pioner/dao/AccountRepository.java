package com.example.pioner.dao;

import com.example.pioner.dao.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{
    Account findByUserId(Long userId);
}
