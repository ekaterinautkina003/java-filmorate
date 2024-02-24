package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.storage.impl.FilmsGenresDbStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmFilmGenreService {

    private final FilmsGenresDbStorage filmsGenresDbStorage;

    public List<FilmGenre> getByFilmId(Long filmId) {
        List<FilmGenre> res = filmsGenresDbStorage.getByFilmId(filmId);
        return res == null ? Collections.emptyList() : res;
    }

    public void add(Long filmId, Long genreId) {
        filmsGenresDbStorage.add(filmId, genreId);
    }

    public void remove(Long filmId) {
        filmsGenresDbStorage.remove(filmId);
    }
}
