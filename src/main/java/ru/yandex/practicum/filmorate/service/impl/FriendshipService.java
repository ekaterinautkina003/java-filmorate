package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.FriendshipDbStorage;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipDbStorage friendshipStorage;

    public List<User> getAllFriends(Long userId) {
        log.info("getAllFriends: {}", userId);
        return friendshipStorage.getAllFriends(userId);
    }

    public List<User> getCrossFriends(Long userId, Long otherUserId) {
        log.info("getCrossFriends: {} , {}", userId, otherUserId);
        return friendshipStorage.getCrossFriends(userId, otherUserId);
    }

    public void addFriend(Long userId, Long friendId) {
        log.info("addFriend: {}, {}", userId, friendId);
        friendshipStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        log.info("deleteFriend: {}, {}", userId, friendId);
        friendshipStorage.deleteFriend(userId, friendId);
    }
}
