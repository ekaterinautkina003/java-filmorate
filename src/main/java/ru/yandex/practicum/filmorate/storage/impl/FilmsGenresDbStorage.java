package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmsGenresDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public List<FilmGenre> getByFilmId(Long id) {
        String query = "select * from filmorate.films_genre where id in (" +
                " select film_genre_id from filmorate.film_film_genre where film_id = ?)";
        return jdbcTemplate.query(query, new Object[]{id}, new FilmGenreDbStorage.FilmGenreMapper());
    }

    public void add(Long filmId, Long genreId) {
        jdbcTemplate.update("INSERT INTO filmorate.film_film_genre (film_id, film_genre_id) VALUES (?, ?)", filmId, genreId);
    }

    public void remove(Long filmId) {
        String deleteSql = "DELETE FROM filmorate.film_film_genre WHERE film_id = ?";
        jdbcTemplate.update(deleteSql, filmId);
    }
}
