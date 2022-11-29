package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface Storage<T> {

    List<T> getAll();
    T getById(long id);
    T update(T data);
    T addData(T data);
    void deleteById(long id);
}