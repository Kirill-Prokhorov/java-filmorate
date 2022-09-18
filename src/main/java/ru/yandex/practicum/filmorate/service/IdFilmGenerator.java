package ru.yandex.practicum.filmorate.service;

public class IdFilmGenerator {

    private static long idFilm = 0;

    public static long getId() {

        return ++idFilm;
    }
}
