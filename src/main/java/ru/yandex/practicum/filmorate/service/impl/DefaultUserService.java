package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

  private final InMemoryUserStorage userStorage;

  @Override
  public User getById(Long id) {
    return userStorage.getById(id);
  }

  @Override
  public User add(User user) {
    return userStorage.add(user);
  }

  @Override
  public User update(User user) {
    return userStorage.update(user);
  }

  @Override
  public Collection<User> getAll() {
    return userStorage.getAll();
  }

  @Override
  public void addFriend(Long userId, Long friendId) {
    User user = getById(userId);
    User friend = getById(friendId);
    user.getFriends().add(friendId);
    friend.getFriends().add(userId);
    update(user);
  }

  @Override
  public void deleteFriend(Long userId, Long friendId) {
    User user = getById(userId);
    User friend = getById(friendId);
    user.getFriends().remove(friendId);
    friend.getFriends().remove(userId);
    update(user);
  }

  @Override
  public List<User> getAllFriends(Long userId) {
    return getById(userId).getFriends()
            .stream()
            .map(this::getById)
            .collect(Collectors.toList());
  }

  @Override
  public List<User> getCrossFriends(Long userId, Long otherUserId) {
    Set<Long> otherUserFriendIds = getById(otherUserId).getFriends();
    return getById(userId).getFriends()
            .stream()
            .filter(otherUserFriendIds::contains)
            .map(this::getById)
            .collect(Collectors.toList());
  }
}
