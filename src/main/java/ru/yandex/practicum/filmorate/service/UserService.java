package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.List;

@Slf4j
@Service
public class UserService implements GeneralService<User>{

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {

        this.userStorage = userStorage;
    }

    @Override
    public List<User> getAll() {

        return userStorage.getAll();
    }

    @Override
    public User addData(User data) {

        if (data.getLogin().contains(" ")) {
            log.warn("Логин некорректен - " + data.getLogin());
            throw new BadRequestException("Ошибка в логине.");
        }

        if(!StringUtils.hasText(data.getName())) {
            log.info("Нет Имени пользователя. Будет использоваться Login");
            data.setName(data.getLogin());
        }

        userStorage.addData(data);
        log.info("Пользователь успешно добавлен");
        return data;
    }

    @Override
    public User getById(long id) {

        if (id <= 0) {
            log.warn("Айди меньше или равен нулю");
            throw new NotFoundException("Нет пользователя с айди: " + id);
        }
        return userStorage.getById(id);
    }

    @Override
    public User update(User data) {

        if (data.getLogin().contains(" ")) {
            log.warn("Логин некорректен - " + data.getLogin());
            throw new BadRequestException("Ошибка в логине.");
        }

        if (getById(data.getId()) != null) {
            if(!StringUtils.hasText(data.getName())) {

            log.info("Нет Имени пользователя. Будет использоваться Login");
            data.setName(data.getLogin());
            }
            userStorage.update(data);
            log.info("Юзер успешно обновлен");
        }
        else {
            throw new NotFoundException("Пользователя не существует");
        }
        return data;
    }

    @Override
    public void deleteById(long id) {

        if (userStorage.getById(id) != null) {
            userStorage.deleteById(id);
            log.info("Пользователь успешно удален");
        } else {
            log.warn("Запрос на удаление отсутствующего пользователя");
            throw new NotFoundException("Пользователя не существует");
        }
    }

    public void addFriend(long id, long friendId) {
        if (userStorage.getById(friendId) != null) {
            userStorage.addFriend(id, friendId);
            return;
        }

        log.warn("Запрос на добавление в друзья несуществующего пользователя.");
        throw new BadRequestException(String.format("Пользователь с ID: %d не существует", friendId));

    }

    public void deleteFriend(long id, long friendId) {

        userStorage.deleteFriend(id, friendId);
    }

    public List<User> getAllFriends(long id) {

        List<User> friendList = userStorage.getFriends(id);
        log.info("Список друзей сформирован");
        return friendList;
    }

    public List<User> getCommonFriends(long id, long friendId) {

        List<User> commonFriendList = userStorage.getCommonFriends(id, friendId);
        log.info("Выведены общие друзья");
        return commonFriendList;
    }
}
