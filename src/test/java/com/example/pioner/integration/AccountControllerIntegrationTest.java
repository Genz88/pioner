package com.example.pioner.integration;

import com.example.pioner.dao.AccountRepository;
import com.example.pioner.dao.UserRepository;
import com.example.pioner.dao.entity.Account;
import com.example.pioner.dao.entity.User;
import com.example.pioner.security.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class AccountControllerIntegrationTest extends AbstractIntegrationTest
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Long fromUserId = 1L;
    private Long toUserId = 2L;

    @BeforeEach
    void setUp()
    {
        accountRepository.deleteAll();
        userRepository.deleteAll();

        User user1 = new User();
        user1.setName("User 1");
        user1.setPassword("test1");
        user1.setDateOfBirth(LocalDate.of(2020, 1, 8));
        userRepository.save(user1);
        fromUserId = user1.getId();

        User user2 = new User();
        user2.setName("User 2");
        user2.setPassword("test2");
        user2.setDateOfBirth(LocalDate.of(2024, 10, 7));
        userRepository.save(user2);
        toUserId = user2.getId();

        Account account1 = new Account();
        account1.setUser(user1);
        account1.setBalance(BigDecimal.valueOf(1000.00));
        account1.setInitialDeposit(BigDecimal.valueOf(1000.00));
        accountRepository.save(account1);

        Account account2 = new Account();
        account2.setUser(user2);
        account2.setBalance(BigDecimal.valueOf(500.00));
        account2.setInitialDeposit(BigDecimal.valueOf(500.00));
        accountRepository.save(account2);
    }

    @AfterEach
    void tearDown()
    {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testTransferMoney_Success() throws Exception
    {
        BigDecimal amount = BigDecimal.valueOf(50.00);

        String token = generateJwtToken(fromUserId);

        mockMvc.perform(post("/accounts/transfer")
            .header("Authorization", "Bearer " + token)
            .param("toUserId", String.valueOf(toUserId))
            .param("amount", String.valueOf(amount))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Translation completed successfully"));
    }


    private String generateJwtToken(Long userId)
    {
        return JwtUtil.generateToken(userId);
    }
}