package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

public interface Service<T> {

  T getById(Long id);

  T add(T t);

  T update(T t);

  Collection<T> getAll();
}
