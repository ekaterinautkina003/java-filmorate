package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.EntityService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements EntityService<User> {

  private Map<Long, User> users = new HashMap<>();

  @Override
  public User add(User entity) {
    users.put(entity.getId(), entity);
    log.info("Create new user: {}", entity);
    return entity;
  }

  @Override
  public User update(User entity) {
    users.put(entity.getId(), entity);
    log.info("Update user with id: {}, entity: {}", entity.getId(), entity);
    return entity;
  }

  @Override
  public Collection<User> getAll() {
    return users.values();
  }
}
