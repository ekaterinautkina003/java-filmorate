package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.Service;
import ru.yandex.practicum.filmorate.storage.impl.FilmDbStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmService implements Service<Film> {

    private final FilmDbStorage filmStorage;
    private final MpaRatingService mpaRatingService;
    private final FilmGenreService filmGenreService;
    private final FilmFilmGenreService filmFilmGenreService;

    @Override
    public Film getById(Long id) {
        Film film = null;
        try {
            log.info("getById: {}", id);
            film = filmStorage.getById(id);
            List<FilmGenre> genres = filmFilmGenreService.getByFilmId(id);
            film.setGenres(genres == null ? Collections.emptyList() : genres);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EntityNotFoundException(Film.class, id);
        }
        return film;
    }


    @Override
    public Film add(Film film) {
        log.info("add: {}", film);
        if (film.getMpa() != null) {
            Optional.of(film)
                    .map(Film::getMpa)
                    .map(MpaRating::getId)
                    .map(mpaRatingService::getById)
                    .orElseThrow(() -> new EntityNotFoundException(MpaRating.class));
            try {
                mpaRatingService.getById(film.getMpa().getId());
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new EntityNotFoundException(MpaRating.class, film.getMpa().getId());
            }
        }
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            for (FilmGenre g : film.getGenres()) {
                try {
                    filmGenreService.getById(g.getId());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new EntityNotFoundException(FilmGenre.class, g.getId());
                }
            }
        }
        filmStorage.add(film);
        List<FilmGenre> genres = film.getGenres();
        film = getByName(film.getName());
        if (genres != null) {
            for (FilmGenre g : genres) {
                filmFilmGenreService.add(film.getId(), g.getId());
            }
        }
        film.setGenres(filmFilmGenreService.getByFilmId(film.getId()));
        return film;
    }

    public Film getByName(String name) {
        return filmStorage.getByName(name);
    }

    @Override
    public Film update(Film film) {
        log.info("update: {}", film);
        filmStorage.update(film);
        Set<Long> genreIds = Optional.of(film)
                .map(Film::getGenres)
                .map(l -> l.stream().map(FilmGenre::getId).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        filmFilmGenreService.remove(film.getId());
        genreIds.forEach(gId -> filmFilmGenreService.add(film.getId(), gId));
        return getById(film.getId());
    }

    @Override
    public List<Film> getAll() {
        Collection<Film> res = filmStorage.getAll();
        res.forEach(f -> f.setGenres(filmFilmGenreService.getByFilmId(f.getId())));
        return List.copyOf(res);
    }
}
