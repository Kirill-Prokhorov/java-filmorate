package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.exception.NoSuchItemException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final LocalDate TEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private static long DURATION = 200;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void createFilmTest() throws Exception {

        Film film = new Film(0, "TestName", "TestDescription",
                TEST_RELEASE_DATE, DURATION);
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(
                        post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void exceptionCreateFilm() throws Exception {

        Film film = new Film(0, "", "TestDescription", TEST_RELEASE_DATE, DURATION);
        String body = objectMapper.writeValueAsString(film);

        this.mockMvc.perform(
                        post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException));
    }

    @Test
    void updateFilmWithBadIdTest() throws Exception {

        Film film = new Film(0, "TestName", "TestDescription", TEST_RELEASE_DATE, DURATION);
        String body = objectMapper.writeValueAsString(film);
        mockMvc.perform(
                        put("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchItemException));
    }

    @Test
    void updateFilmWithNonexistentIdTest() throws Exception {

        Film film = new Film(1, "TestName", "TestDescription", TEST_RELEASE_DATE, DURATION);
        String body = objectMapper.writeValueAsString(film);
        mockMvc.perform(
                        put("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchItemException));
    }

    @Test
    void badReleaseDateCreateFilmTest() throws Exception {

        Film film = new Film(0, "TestName", "TestDescription",
                TEST_RELEASE_DATE.minusDays(1), DURATION);
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(
                post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void badDescriptionCreateFilmTest() throws Exception {

        Film film = new Film(0, "TestName", "TestDescription".repeat(200),
                TEST_RELEASE_DATE, DURATION);
        String body = objectMapper.writeValueAsString(film);

        mockMvc.perform(
                        post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

}