package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.service.Service;
import ru.yandex.practicum.filmorate.storage.impl.FilmGenreDbStorage;

import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmGenreService implements Service<FilmGenre> {

    private final FilmGenreDbStorage filmGenreDbStorage;

    @Override
    public FilmGenre getById(Long id) {
        try {
            log.info("getById: {}", id);
            return filmGenreDbStorage.getById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EntityNotFoundException(FilmGenre.class, id);
        }
    }

    @Override
    public FilmGenre add(FilmGenre filmGenre) {
        log.info("add: {}", filmGenre);
        return filmGenreDbStorage.add(filmGenre);
    }

    @Override
    public FilmGenre update(FilmGenre filmGenre) {
        log.info("update: {}", filmGenre);
        return filmGenreDbStorage.update(filmGenre);
    }

    @Override
    public Collection<FilmGenre> getAll() {
        return filmGenreDbStorage.getAll();
    }

    public boolean isExistsByName(String name) {
        return filmGenreDbStorage.isExistsByName(name);
    }
}
