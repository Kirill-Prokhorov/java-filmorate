package ru.yandex.practicum.filmorate.service;

import java.util.List;

public interface GeneralService<T> {

    List<T> getAll();
    T addData(T data);
    T getById(long id);
    T update(T data);
    void deleteById(long id);
}
