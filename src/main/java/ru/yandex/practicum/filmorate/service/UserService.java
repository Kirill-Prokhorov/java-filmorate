package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService implements GeneralService<User>{

    private final UserStorage<User> storage;

    @Autowired
    public UserService(UserStorage<User> storage) {
        this.storage = storage;
    }

    @Override
    public List<User> getAll() {
        return storage.getAll();
    }

    @Override
    public User addData(User data) {

        if(!StringUtils.hasText(data.getName())) {

            log.info("Нет Имени пользователя. Будет использоваться Login");
            data.setName(data.getLogin());
        }

        return storage.addData(data);
    }

    @Override
    public User getById(long id) {
        return storage.getById(id);
    }

    @Override
    public User update(User data) {
        return storage.update(data);
    }

    @Override
    public void deleteById(long id) {
        storage.deleteById(id);
    }

    public void addFriend(long id, long friendId) {
        if (storage.getById(friendId) != null) {
            storage.getById(id).getFriendList().add(friendId);
            storage.getById(friendId).getFriendList().add(id);
            return;
        }

        log.warn("Запрос на добавление в друзья несуществующего пользователя.");
        throw new BadRequestException(String.format("Пользователь с ID: %d не существует", friendId));

    }

    public void deleteFriend(long id, long friendId) {
        if (storage.getById(id).getFriendList().contains(friendId)) {
            storage.getById(id).getFriendList().remove(friendId);
            storage.getById(friendId).getFriendList().remove(id);
        } else {

            log.warn("Запрос на удаление из друзей несуществующего друга");
            throw new BadRequestException(String.format("Друг с ID: %d не существует", friendId));
        }
    }

    public List<User> getAllFriends(long id) {
        List<User> friendList = new ArrayList<>();
        for (Long friendId : storage.getById(id).getFriendList()) {
            friendList.add(storage.getById(friendId));
        }
        return friendList;
    }

    public List<User> getCommonFriends(long id, long friendId) {
        List<User> commonFriendList = new ArrayList<>();
        for (Long commonFriendId : storage.getById(id).getFriendList()) {
            if (storage.getById(friendId).getFriendList().contains(commonFriendId)) {
                commonFriendList.add(storage.getById(commonFriendId));
            }
        }
        return commonFriendList;
    }
}
