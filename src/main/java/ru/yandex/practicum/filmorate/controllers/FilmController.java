package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoSuchItemException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.IdFilmGenerator;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private Map<Long,Film> storage = new HashMap<>();

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {

        film.setId(IdFilmGenerator.getId());

        if (film.getDescription().length() > 200) {

            log.warn("Неправильная длина описания");

            throw new ValidationException("Описание более 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {

            log.warn("Нужен релиз после 1985.12.28");
            throw new ValidationException("Дата релиза не соответсвует требованиям =(");
        }

        storage.put(film.getId(), film);

        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {

        if(film.getId() < 1){

            log.warn("В PUT/films запросе указан ID меньше 1");
            throw new NoSuchItemException("Id не соответствует требованиям!");
        }

        if (!storage.containsKey(film.getId())) {

            log.warn("В PUT/films запросе нет фильма с указанным ID");
            throw new NoSuchItemException("Нет фильма с ID - " + film.getId());
        }

        storage.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public List<Film> films() {

        return new ArrayList<>(storage.values());
    }
}
