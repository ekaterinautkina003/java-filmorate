package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultFilmService implements FilmService {

  private final InMemoryFilmStorage filmStorage;

  @Override
  public void addLike(Long filmId, Long userId) {
    Film film = getById(filmId);
    film.getLikedUsers().add(userId);
  }

  @Override
  public void removeLike(Long filmId, Long userId) {
    Film film = getById(filmId);
    film.getLikedUsers().remove(userId);
  }

  @Override
  public List<Film> getPopular(Integer count) {
    if (count == 0)
      return Collections.emptyList();
    Collection<Film> allFilms = filmStorage.films.values();
    TreeSet<Film> treeSet = new TreeSet<>(Comparator.comparingInt(film -> film.getLikedUsers().size()));
    treeSet.addAll(allFilms);
    return treeSet.stream()
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
