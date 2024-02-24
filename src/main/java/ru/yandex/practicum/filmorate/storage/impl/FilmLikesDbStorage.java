package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmLikesDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public void addLike(Long filmId, Long userId) {
        jdbcTemplate.update("insert into filmorate.films_like (film_id, user_id) values (?, ?)",
                filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        jdbcTemplate.update("delete from filmorate.films_like where film_id=? and user_id=?",
                filmId, userId);
    }

    public List<Film> getPopular(int count) {
        String query = "SELECT f.*, f.id as p_film_id, m.id as m_mpa_id, m.name as m_mpa_name " +
                "FROM filmorate.films_like fl " +
                "RIGHT JOIN filmorate.films f ON f.id = fl.film_id " +
                "JOIN filmorate.mpa m on m.id = f.mpa_id " +
                "GROUP BY fl.film_id, f.id, m.id " +
                "ORDER BY COUNT(fl.film_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(query, new Object[]{count}, new FilmMapper());
    }

    private static class FilmMapper implements RowMapper<Film> {
        @Override
        public Film mapRow(ResultSet resultSet, int i) throws SQLException {
            MpaRating rating = new MpaRating();
            rating.setId(resultSet.getLong("m_mpa_id"));
            rating.setName(resultSet.getString("m_mpa_name"));
            return Film.builder()
                    .id(resultSet.getLong("p_film_id"))
                    .name(resultSet.getString("name"))
                    .description(resultSet.getString("description"))
                    .rate(resultSet.getInt("rate"))
                    .mpa(rating)
                    .releaseDate(resultSet.getObject("release_date", LocalDate.class))
                    .duration(resultSet.getInt("duration"))
                    .build();
        }
    }
}
