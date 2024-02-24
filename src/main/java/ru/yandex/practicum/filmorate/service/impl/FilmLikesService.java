package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.FilmLikesDbStorage;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmLikesService {

    private final UserService userService;
    private final FilmService filmService;
    private final FilmFilmGenreService filmFilmGenreService;
    private final FilmLikesDbStorage filmLikesDbStorage;

    public void addLike(Long filmId, Long userId) {
        log.info("addLike: {}, {}", filmId, userId);
        try {
            filmService.getById(filmId);
        } catch (Exception e) {
            throw new EntityNotFoundException(Film.class, filmId);
        }
        try {
            userService.getById(userId);
        } catch (Exception r) {
            throw new EntityNotFoundException(User.class, userId);
        }
        filmLikesDbStorage.addLike(filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        log.info("removeLike: {}, {}", filmId, userId);
        try {
            filmService.getById(filmId);
        } catch (Exception e) {
            throw new EntityNotFoundException(Film.class, filmId);
        }
        try {
            userService.getById(userId);
        } catch (Exception r) {
            throw new EntityNotFoundException(User.class, userId);
        }
        filmLikesDbStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopular(int count) {
        log.info("getPopular: {}", count);
        if (count == 0) {
            return Collections.emptyList();
        }
        List<Film> films = filmLikesDbStorage.getPopular(count);
        films.forEach(f -> f.setGenres(filmFilmGenreService.getByFilmId(f.getId())));
        return films;
    }
}
