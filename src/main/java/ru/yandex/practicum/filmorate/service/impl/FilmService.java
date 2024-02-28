package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.service.Service;
import ru.yandex.practicum.filmorate.storage.impl.FilmDbStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmService implements Service<Film> {

    private final FilmDbStorage filmStorage;
    private final FilmFilmGenreService filmFilmGenreService;

    @Override
    public Film getById(Long id) {
        log.info("getById: {}", id);
        Film film = filmStorage.getById(id);
        film.setGenres(filmFilmGenreService.getByFilmId(id));
        return film;
    }

    @Override
    public Film add(Film film) {
        log.info("add: {}", film);
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
        List<Long> filmIds = res.stream().map(Film::getId).collect(Collectors.toList());
        Map<Long, List<FilmGenre>> genresList = filmFilmGenreService.getByListFilmId(filmIds);
        res.forEach(f -> f.setGenres(genresList.getOrDefault(f.getId(), new ArrayList<>())));
        return List.copyOf(res);
    }
}
