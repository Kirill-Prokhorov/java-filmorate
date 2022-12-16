package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import java.util.List;
import java.time.LocalDate;

@Slf4j
@Service
public class FilmService implements GeneralService<Film>{

    private final FilmStorage filmStorage;
    private final GenreService genreService;

    @Autowired
    public FilmService(FilmStorage filmStorage, GenreService genreService) {

        this.filmStorage = filmStorage;
        this.genreService = genreService;
    }

    @Override
    public List<Film> getAll() {

        return filmStorage.getAll();
    }

    @Override
    public Film addData(Film data) {

        if (data.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {

            log.warn("Нужен релиз после 1985.12.28");
            throw new BadRequestException("Дата релиза не соответсвует требованиям =(");
        }
        filmStorage.addData(data);

        if (data.getGenres() != null && data.getGenres().size() > 0) {
            genreService.addGenresToFilm(data);
        }
        return data;
    }

    @Override
    public Film getById(long id) {

        if (id <= 0) {
            log.warn("Айди меньше или равен нулю");
            throw new NotFoundException("Некорректный айди фильма: " + id);
        }
        return filmStorage.getById(id);
    }

    @Override
    public Film update(Film data) {

        if (data.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {

            log.warn("Нужен релиз после 1985.12.28");
            throw new BadRequestException("Дата релиза не соответсвует требованиям =(");
        }
        if (data.getId() <= 0) {
            log.warn("Айди меньше или равен нулю");
            throw new NotFoundException("Некорректный айди фильма: " + data.getId() );
        }
        genreService.addGenresToFilm(data);
        filmStorage.update(data);
        return data;
    }

    @Override
    public void deleteById(long id) {

        if (filmStorage.getById(id) != null) {
            filmStorage.deleteById(id);
            log.info("Фильм удалили успешно");
        } else {
            log.error("Запрос на удаление отсутствующего фильма");
            throw new NotFoundException("Данный фильм отсутствует");
        }
    }

    public void addLike(long id, long userId) {

        if (filmStorage.hasUsersLike(id, userId)) {
            log.warn("Есть лайк от этого пользователя");
            throw new BadRequestException("Уже есть лайк");
        } else {
            filmStorage.addLike(id, userId);
            log.info("Лайк добавлен");
        }
    }

    public void deleteLike(long id, long userId) {

        if (filmStorage.hasUsersLike(id, userId)) {
            filmStorage.deleteLike(id, userId);
            log.info("Лайк успешно удален");
        } else {
            log.warn("Нет лайка от пользователя");
            throw new NotFoundException("Нет лайка от этого пользователя");
        }
    }

    public List<Film> getTopFilms(int size) {

        return filmStorage.getTopFilms(size);
    }
}
