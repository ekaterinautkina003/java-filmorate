package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements Storage<User> {

    public final Map<Long, User> users = new HashMap<>();

    private Long id = 1L;

    @Override
    public User getById(Long id) {
        if (!users.containsKey(id))
            throw new EntityNotFoundException(User.class, id);
        return users.get(id);
    }

    @Override
    public User add(User entity) {
        entity.setId(generationId());
        users.put(entity.getId(), entity);
        log.info("Create new user: {}", entity);
        return entity;
    }

    @Override
    public User update(User entity) {
        User user = users.get(entity.getId());
        user.setName(entity.getName());
        user.setLogin(entity.getLogin());
        user.setEmail(entity.getEmail());
        user.setBirthday(entity.getBirthday());
        user.setFriends(entity.getFriends());
        users.put(user.getId(), user);
        log.info("Update user with id: {}, entity: {}", entity.getId(), entity);
        return entity;
    }

    @Override
    public List<User> getAll() {
        return List.copyOf(users.values());
    }

    private Long generationId() {
        return id++;
    }
}