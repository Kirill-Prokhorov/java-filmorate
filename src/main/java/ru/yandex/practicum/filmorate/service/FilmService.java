package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService implements GeneralService<Film>{

    private final FilmStorage storage;

    public FilmService(FilmStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<Film> getAll() {
        return storage.getAll();
    }

    @Override
    public Film addData(Film data) {

        return storage.addData(data);
    }

    @Override
    public Film getById(long id) {
        return storage.getById(id);
    }

    @Override
    public Film update(Film data) {

        return storage.update(data);
    }

    @Override
    public void deleteById(long id) {
        storage.deleteById(id);
    }

    public void addLike(long id, long userId) {
        storage.getById(id).getLikesByUserIds().add(userId);
        storage.getById(id).setRate(storage.getById(id).getLikesByUserIds().size());
    }

    public void deleteLike(long id, long userId) {
        if (storage.getById(id).getLikesByUserIds().contains(userId)) {
            storage.getById(id).getLikesByUserIds().remove(userId);
            storage.getById(id).setRate(storage.getById(id).getLikesByUserIds().size());
        } else {
            log.warn("Запрос на удаление отсутствующего лайка");
            throw new NotFoundException("Лайк данного пользователя под этим фильмом отсутствует");
        }
    }

    public List<Film> getTopFilms(int size) {
        return storage.getAll().stream()
                .sorted(Comparator.comparing(Film::getRate))
                .limit(size)
                .collect(Collectors.toList());

    }
}
