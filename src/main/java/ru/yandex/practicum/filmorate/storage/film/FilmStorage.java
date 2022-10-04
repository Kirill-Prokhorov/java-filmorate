package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;
import java.util.Set;

public interface FilmStorage extends Storage<Film> {

    void addLike(long id, long userId);
    void removeLike(long id, long userId);
    List<Film> getTopFilms(int size);
    boolean hasUsersLike(long id, long userId);
    Set<Long> getFilmsUserLikes(long id);
}