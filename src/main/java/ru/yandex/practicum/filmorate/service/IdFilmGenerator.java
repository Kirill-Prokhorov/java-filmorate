package ru.yandex.practicum.filmorate.service;

public class IdFilmGenerator {

    private static long id = 0L;

    public static long getFilmId() {

        return ++id;
    }
}
