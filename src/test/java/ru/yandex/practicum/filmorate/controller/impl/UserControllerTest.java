package ru.yandex.practicum.filmorate.controller.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.ControllerTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends ControllerTest {

    private static final String USER_REQUEST_MAPPING = "/users";

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(2)
    @SneakyThrows
    void update() {
        User user = new User();
        user.setId(1L);
        user.setName("new user");
        user.setLogin("NWE_USER_LOGIN");
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.of(2012, 1, 19));
        String jsonUser = gson.toJson(user);

        mockMvc.perform(post(USER_REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUser))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @SneakyThrows
    void getAll() {
        mockMvc.perform(get(USER_REQUEST_MAPPING))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @ValueSource(strings = {"", "fsdafdsf", "_ _@mail"})
    @ParameterizedTest
    void validateEmail(String email) {
        User user = new User();
        user.setId(1L);
        user.setName("new user");
        user.setLogin("NWE_USER_LOGIN");
        user.setEmail(email);
        user.setBirthday(LocalDate.of(2012, 1, 19));
        String jsonUser = gson.toJson(user);

        mockMvc.perform(post(USER_REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUser))
                .andExpect(status().is4xxClientError());
    }

    @ValueSource(strings = {"", "login with space"})
    @ParameterizedTest
    @SneakyThrows
    void validateLogin(String login) {
        User user = new User();
        user.setId(1L);
        user.setName("new user");
        user.setLogin(login);
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.of(2012, 1, 19));
        String jsonUser = gson.toJson(user);

        mockMvc.perform(post(USER_REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUser))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    void validateBirthday() {
        User user = new User();
        user.setId(1L);
        user.setName("asdfdsf");
        user.setLogin("NWE_USER_LOGIN");
        user.setEmail("user@mail.ru");
        user.setBirthday(LocalDate.now().plusYears(1));
        String jsonUser = gson.toJson(user);

        mockMvc.perform(post(USER_REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUser))
                .andExpect(status().is4xxClientError());
    }
}
