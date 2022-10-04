package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage<User>{

    private Map<Long, User> storage = new HashMap<>();

    @Override
    public List<User> getAll() {

        return new ArrayList<>(storage.values());
    }

    @Override
    public User getById(long id) {

        if(storage.containsKey(id)) {
            return storage.get(id);
        }
        throw new NotFoundException(String.format("Не найдено пользователя с ID = %d", id));
    }

    @Override
    public User update(User data) {

        log.info("Обрабатываем Put/users запрос");

        if(data.getId() < 1 ) {

            log.warn("Проблема с id в методе Put");
            throw new NotFoundException("Id пользователя не соответствует требованиям!");
        }

        log.info("Хотим обновить пользователя");
        if (!storage.containsKey(data.getId())) {

            log.warn("В методе PUT/users пытаетесь обновить пользователя с несуществующим ID");
            throw new NotFoundException("Нет пользователя с ID - " + data.getId());
        }

        log.info("Хотим вернуть пользователя");
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public User addData(User data) {

        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public void deleteById(long id) {

        storage.remove(id);
    }

    @Override
    public void addFriend(long id, long friendId) {

    }

    @Override
    public void deleteFriend(long id, long friendId) {

    }

    @Override
    public List<User> getFriends(long id) {
        return null;
    }

    @Override
    public List<User> getCommonFriends(long id, long friendId) {
        return null;
    }
}
