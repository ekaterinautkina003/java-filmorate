package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.UserDbStorage;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDbStorage userStorage;

    public User getById(Long id) {
        log.info("getById: {}", id);
        return userStorage.getById(id);
    }

    public User add(User user) {
        log.info("add: {}", user);
        return userStorage.add(user);
    }

    public User update(User user) {
        try {
            User u = getById(user.getId());
            log.info("update: {}", user);
            return userStorage.update(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EntityNotFoundException(User.class, user.getId());
        }
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }
}
