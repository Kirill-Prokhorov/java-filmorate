package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.IdUserGenerator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private Map<Long, User> storage = new HashMap<>();

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {

        user.setId(IdUserGenerator.getIdUser());

        if(!StringUtils.hasText(user.getName())) {

            log.info("Нет Имени пользователя. Будет использоваться Login");
            user.setName(user.getLogin());
        }
        storage.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user)  {

        log.info("Обрабатываем Put/users запрос");

        if(user.getId() < 1 ) {

            log.warn("Проблема с id в методе Put");
            throw new NotFoundException("Id пользователя не соответствует требованиям!");
        }

        log.info("Хотим обновить пользователя");
        if (!storage.containsKey(user.getId())) {

            log.warn("В методе PUT/users пытаетесь обновить пользователя с несуществующим ID");
            throw new NotFoundException("Нет пользователя с ID - " + user.getId());
        }

        log.info("Хотим вернуть пользователя");
        storage.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public List<User> users() {

        return new ArrayList<>(storage.values());
    }
}
