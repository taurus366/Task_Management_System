package com.system.management.web;

import com.system.management.model.binding.LoginUserBindingModel;
import com.system.management.model.binding.RegisterNewUserBindingModel;
import com.system.management.model.entity.AccountEntity;
import com.system.management.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;



    @Test
    void login() throws Exception {
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content("{\"email\":\"taurus366@abv.bg\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void register() throws  Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content("{\"email\":\"test12345@abv.bg\"," +
                                "\"firstName\":\"test1234\"," +
                                "\"lastName\":\"test1234\"}"))
                .andExpect(status().isOk());
    }

    @AfterEach
    void cleanDB() {

        AccountEntity accountEntity = accountRepository.getAccountEntityByEmail("test12345@abv.bg").orElse(null);

        this.accountRepository
                .delete(accountEntity);
    }

}
