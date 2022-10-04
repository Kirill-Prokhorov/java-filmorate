package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {

        log.warn("В POST/films обрабатываем запрос на создание фильма");
        return filmService.addData(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {

        log.warn("В PUT/films обрабатываем запрос на обновление фильма");
        return filmService.update(film);
    }

    @GetMapping
    public List<Film> films() {

        log.warn("В GET/films обрабатываем запрос на список всех фильмов");
        return filmService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Film getFilmById(@PathVariable Long id) {
        log.info("В GET/films обработка запрос на поиск фильма по ID");
        return filmService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteFilmById(@PathVariable Long id) {
        log.info("В DELETE/films обработка запроса на удаление фильма по ID");
        filmService.deleteById(id);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Запрос на добавление лайка");
        filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Запрос на удаление лайка");
        filmService.deleteLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Запрос на выдачу популярных фильмов");
        return filmService.getTopFilms(count);
    }
}
