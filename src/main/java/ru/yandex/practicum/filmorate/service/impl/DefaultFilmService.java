package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultFilmService implements FilmService {

  private final InMemoryUserStorage userStorage;
  private final InMemoryFilmStorage filmStorage;

  @Override
  public void addLike(Long filmId, Long userId) {
    Film film = getById(filmId);
    User user = userStorage.getById(userId);
    film.getLikedUsers().add(user.getId());
    update(film);
  }

  @Override
  public void removeLike(Long filmId, Long userId) {
    Film film = getById(filmId);
    User user = userStorage.getById(userId);
    film.getLikedUsers().remove(user.getId());
    update(film);
  }

  @Override
  public List<Film> getPopular(Integer count) {
    if (count == 0)
      return Collections.emptyList();
    return getAll()
            .stream()
            .sorted(Comparator.comparingInt(film -> ((Film) film).getLikedUsers().size()).reversed())
            .limit(count)
            .collect(Collectors.toList());
  }

  @Override
  public Film getById(Long id) {
    return filmStorage.getById(id);
  }

  @Override
  public Film add(Film film) {
    return filmStorage.add(film);
  }

  @Override
  public Film update(Film film) {
    return filmStorage.update(film);
  }

  @Override
  public Collection<Film> getAll() {
    return filmStorage.getAll();
  }
}
