package com.example.pioner.dao;

import com.example.pioner.dao.entity.PhoneData;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneData, Long>
{

    @Cacheable(value = "phone", key = "#phone")
    PhoneData findByPhone(String phone);

    List<PhoneData> findAllByUserId(Long userId);
}
