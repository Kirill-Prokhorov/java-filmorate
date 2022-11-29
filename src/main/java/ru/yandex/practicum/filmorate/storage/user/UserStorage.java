package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.List;

public interface UserStorage<U> extends Storage<User> {

    void addFriend(long id, long friendId);
    void deleteFriend(long id, long friendId);
    List<User> getFriends(long id);
    List<User> getCommonFriends(long id, long friendId);

}