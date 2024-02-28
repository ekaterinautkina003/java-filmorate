package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.storage.impl.FilmsGenresDbStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FilmFilmGenreService {

    private final FilmsGenresDbStorage filmsGenresDbStorage;

    public List<FilmGenre> getByFilmId(Long filmId) {
        return filmsGenresDbStorage.getByFilmId(filmId);
    }

    public Map<Long, List<FilmGenre>> getByListFilmId(List<Long> filmIds) {
        if (filmIds.isEmpty()) {
            return new HashMap<>();
        }
        return filmsGenresDbStorage.getByFilmsIds(filmIds);
    }

    public void add(Long filmId, Long genreId) {
        filmsGenresDbStorage.add(filmId, genreId);
    }

    public void remove(Long filmId) {
        filmsGenresDbStorage.remove(filmId);
    }
}
