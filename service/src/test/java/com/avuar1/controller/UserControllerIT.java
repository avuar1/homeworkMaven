package com.avuar1.controller;

import static com.avuar1.entity.User_.email;
import static com.avuar1.entity.User_.firstName;
import static com.avuar1.entity.User_.lastName;
import static com.avuar1.entity.User_.password;
import static com.avuar1.entity.User_.role;
import com.avuar1.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(3)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(firstName, "Test")
                        .param(lastName, "Test")
                        .param(email, "test@gmail.com")
                        .param(password,"123")
                        .param(role, "ADMIN")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }
}
