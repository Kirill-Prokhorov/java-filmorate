package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Запрошенный объект отсутствует")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {

        super(message);
    }
}