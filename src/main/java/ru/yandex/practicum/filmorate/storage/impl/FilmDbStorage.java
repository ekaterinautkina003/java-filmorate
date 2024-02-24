package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements Storage<Film> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film getById(Long id) {
        String query = "SELECT f.*, m.id as m_mpa_id, m.name as m_mpa_name FROM filmorate.films f join filmorate.mpa m on f.mpa_id = m.id WHERE f.id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, new FilmMapper());
    }

    public Film getByName(String name) {
        String query = "SELECT f.*, m.id as m_mpa_id, m.name as m_mpa_name FROM filmorate.films f join filmorate.mpa m on f.mpa_id = m.id WHERE f.name = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{name}, new FilmMapper());
    }

    @Override
    public Film add(Film entity) {
        jdbcTemplate.update("insert into filmorate.films (name, description, release_date, duration, rate, mpa_id) values (?, ?, ?, ?, ?, ?)",
                entity.getName(), entity.getDescription(), entity.getReleaseDate(), entity.getDuration(), entity.getRate(), entity.getMpa().getId());
        return entity;
    }

    @Override
    public Film update(Film entity) {
        jdbcTemplate.update("update filmorate.films set name=?, description=?, release_date=?, duration=?, mpa_id=? where id=?",
                entity.getName(), entity.getDescription(), entity.getReleaseDate(), entity.getDuration(), entity.getMpa().getId(), entity.getId());
        return entity;
    }

    public void updateMpaByFilmId(Long filmId, Long mpaId) {
        jdbcTemplate.update("update filmorate.films set mpa_id=? where id=?", mpaId, filmId);
    }

    @Override
    public Collection<Film> getAll() {
        String query = "SELECT f.*, m.id as m_mpa_id, m.name as m_mpa_name FROM filmorate.films f join filmorate.mpa m on f.mpa_id = m.id order by id asc";
        return jdbcTemplate.query(query, new FilmMapper());
    }

    public static class FilmMapper implements RowMapper<Film> {
        @Override
        public Film mapRow(ResultSet resultSet, int i) throws SQLException {
            MpaRating rating = new MpaRating();
            rating.setId(resultSet.getLong("m_mpa_id"));
            rating.setName(resultSet.getString("m_mpa_name"));
            return Film.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .description(resultSet.getString("description"))
                    .mpa(rating)
                    .rate(resultSet.getInt("rate"))
                    .releaseDate(resultSet.getObject("release_date", LocalDate.class))
                    .duration(resultSet.getInt("duration"))
                    .build();
        }
    }
}
