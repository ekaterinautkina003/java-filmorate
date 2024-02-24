package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class MpaRatingDbStorage implements Storage<MpaRating> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public MpaRating getById(Long id) {
        String query = "SELECT * FROM filmorate.mpa WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, new MpaRatingMapper());
    }

    @Override
    public MpaRating add(MpaRating entity) {
        jdbcTemplate.update("insert into filmorate.mpa (name) values (?)", entity.getName());
        return entity;
    }

    @Override
    public MpaRating update(MpaRating entity) {
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public Collection<MpaRating> getAll() {
        String query = "SELECT * FROM filmorate.mpa order by id";
        return jdbcTemplate.query(query, new MpaRatingMapper());
    }

    private static class MpaRatingMapper implements RowMapper<MpaRating> {
        @Override
        public MpaRating mapRow(ResultSet resultSet, int i) throws SQLException {
            MpaRating rating = new MpaRating();
            rating.setId(resultSet.getLong("id"));
            rating.setName(resultSet.getString("name"));
            return rating;
        }
    }
}
