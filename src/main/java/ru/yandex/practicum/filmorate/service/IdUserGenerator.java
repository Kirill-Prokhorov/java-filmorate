package ru.yandex.practicum.filmorate.service;

public class IdUserGenerator {

    private static long id = 0L;

    public static long getUserId() {

        return ++id;
    }
}
