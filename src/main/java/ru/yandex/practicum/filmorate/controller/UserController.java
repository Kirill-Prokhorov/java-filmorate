package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.warn("В POST/users обрабатываем запрос на создание пользователя.");
        return userService.addData(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user)  {
        log.warn("В PUT/users обрабатываем запрос на обновление пользователя.");
        return userService.update(user);
    }

    @GetMapping
    public List<User> users() {
        log.warn("В GET/users обрабатываем запрос на список всех пользователей");
        return userService.getAll();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable Long id) {
        log.info("В GET/users обрабатываем запрос на поиск пользователя по ID");
        return userService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("В DELETE/users обрабатываем запрос на удаление пользователя по ID");
        userService.deleteById(id);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addToFriends(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("В PUT/users обрабатываем запрос на добавление друга");
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("В DELETE/users обрабатываем запрос на удаление друга");
        userService.deleteFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        log.info("В GET/users обрабатываем запрос на выдачу всех друзей");
        return userService.getAllFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("В GET/users обрабатываем запрос на выдачу общих друзей");
        return userService.getCommonFriends(id, otherId);
    }
}
