package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.EntityService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FilmService implements EntityService<Film> {

  private Map<Long, Film> films = new HashMap<>();
  private Long id = 1L;

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
