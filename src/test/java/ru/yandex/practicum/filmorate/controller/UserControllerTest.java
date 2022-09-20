package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final LocalDate TEST_BIRTHDAY = LocalDate.now();

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void successfullyCreateUser() throws Exception {

        User userTest = new User(0,"ieri@lnln.ru", "TestLogin", "TestName", TEST_BIRTHDAY);
        String body = objectMapper.writeValueAsString(userTest);

        this.mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void exceptionCreateUser() throws Exception {

        User userTest = new User(0,"ieri@lnln.ru", "TestLogin", "TestName",
                TEST_BIRTHDAY.plusDays(1));
        String body = objectMapper.writeValueAsString(userTest);

        this.mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void notNameCreateUser() throws Exception {

        User userTest = new User(0,"ieri@lnln.ru", "TestLogin","", TEST_BIRTHDAY);
        String body = objectMapper.writeValueAsString(userTest);

        this.mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void emptyLoginCreateUser() throws Exception {

        User userTest = new User(0,"ieri@lnln.ru", "","TestName", TEST_BIRTHDAY);
        String body = objectMapper.writeValueAsString(userTest);

        this.mockMvc.perform(
                        post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException));
    }

    @Test
    public void badIdCreateUser() throws Exception {

        User userTest = new User(1,"ieri@lnln.ru", "TestLogin","TestName", TEST_BIRTHDAY);
        String body = objectMapper.writeValueAsString(userTest);

        this.mockMvc.perform(
                        put("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    public void negativeIdCreateUser() throws Exception {

        User userTest = new User(-1,"ieri@lnln.ru", "TestLogin","TestName", TEST_BIRTHDAY);
        String body = objectMapper.writeValueAsString(userTest);

        this.mockMvc.perform(
                        put("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

}