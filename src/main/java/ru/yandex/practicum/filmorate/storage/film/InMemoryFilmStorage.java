package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Long,Film> storage = new HashMap<>();

    @Override
    public List<Film> getAll() {

        return new ArrayList<>(storage.values());
    }

    @Override
    public Film getById(long id) {
        if(storage.containsKey(id)) {
            return storage.get(id);
        }
        throw new NotFoundException(String.format("Не найдено фильма с ID = %d", id));
    }

    @Override
    public Film update(Film data) {

        if (!storage.containsKey(data.getId())) {

            log.warn("В PUT/films запросе нет фильма с указанным ID");
            throw new NotFoundException("Нет фильма с ID - " + data.getId());
        }
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public Film addData(Film data) {

        if (storage.containsKey(data.getId())) {

            log.warn("Запрос на добавление уже существующего фильма");
            throw new DataNotFoundException(String.format("Фильм %s уже есть в списке.", data.getName()));
        }
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public void deleteById(long id) {

        storage.remove(id);
    }

    @Override
    public void addLike(long id, long userId) {

    }

    @Override
    public void removeLike(long id, long userId) {

    }

    @Override
    public List<Film> getTopFilms(int size) {
        return null;
    }

    @Override
    public boolean hasUsersLike(long id, long userId) {
        return false;
    }

    @Override
    public Set<Long> getFilmsUserLikes(long id) {
        return null;
    }

}

