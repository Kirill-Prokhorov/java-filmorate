package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Валидация не пройдена")
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {

        super(message);
    }
}
