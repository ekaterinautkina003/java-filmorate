package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService extends Service<User> {

  void addFriend(Long userId, Long friendId);

  void deleteFriend(Long userId, Long friendId);

  List<User> getAllFriends(Long userId);

  List<User> getCrossFriends(Long userId, Long otherUserId);
}
