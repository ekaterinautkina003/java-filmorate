package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements Storage<Film> {

  public final Map<Long, Film> films = new HashMap<>();
  private Long id = 1L;

  @Override
  public Film getById(Long id) {
    if (!films.containsKey(id))
      throw new EntityNotFoundException(Film.class, id);
    return films.get(id);
  }

  @Override
  public Film add(Film entity) {
    entity.setId(generationId());
    films.put(entity.getId(), entity);
    log.info("Create new film: {}", entity);
    return entity;
  }

  @Override
  public Film update(Film entity) {
    Film film = films.get(entity.getId());
    film.setDescription(entity.getDescription());
    film.setName(entity.getName());
    film.setReleaseDate(entity.getReleaseDate());
    film.setDuration(entity.getDuration());
    film.setLikedUsers(entity.getLikedUsers());
    films.put(film.getId(), film);
    log.info("Update file with id: {}, entity: {}", entity.getId(), entity);
    return entity;
  }

  @Override
  public List<Film> getAll() {
    return List.copyOf(films.values());
  }

  private Long generationId() {
    return id++;
  }
}
