package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmGenreDbStorage implements Storage<FilmGenre> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public FilmGenre getById(Long id) {
        String query = "SELECT * FROM filmorate.films_genre WHERE id = ?";
        List<FilmGenre> res = jdbcTemplate.query(query, new FilmGenreMapper(), id);
        if (res.isEmpty()) {
            throw new EntityNotFoundException(FilmGenre.class, id);
        }
        return res.get(0);
    }

    @Override
    public FilmGenre add(FilmGenre entity) {
        jdbcTemplate.update("insert into filmorate.films_genre (name) values (?)", entity.getName());
        return entity;
    }

    @Override
    public FilmGenre update(FilmGenre entity) {
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public Collection<FilmGenre> getAll() {
        return jdbcTemplate.query("SELECT * FROM filmorate.films_genre order by id", new FilmGenreMapper());
    }

    public static class FilmGenreMapper implements RowMapper<FilmGenre> {
        @Override
        public FilmGenre mapRow(ResultSet resultSet, int i) throws SQLException {
            FilmGenre genre = new FilmGenre();
            genre.setId(resultSet.getLong("id"));
            genre.setName(resultSet.getString("name"));
            return genre;
        }
    }
}
