package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.EntityService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FilmService implements EntityService<Film> {

    private Map<Long, Film> films = new HashMap<>();

    @Override
    public Film add(Film entity) {
        films.put(entity.getId(), entity);
        log.info("Create new film: {}", entity);
        return entity;
    }

    @Override
    public Film update(Film entity) {
        films.put(entity.getId(), entity);
        log.info("Update file with id: {}, entity: {}", entity.getId(), entity);
        return entity;
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }
}
