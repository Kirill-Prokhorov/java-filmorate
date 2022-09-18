package ru.yandex.practicum.filmorate.service;

public class IdUserGenerator {

    private static long idUser = 0;

    public static long getIdUser() {

        return ++idUser;
    }
}
