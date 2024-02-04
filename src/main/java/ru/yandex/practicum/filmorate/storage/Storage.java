package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> {

  T getById(Long id);

  T add(T entity);

  T update(T entity);

  Collection<T> getAll();
}
