package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

public interface EntityService<T> {

  T add(T entity);

  T update(T entity);

  Collection<T> getAll();
}
