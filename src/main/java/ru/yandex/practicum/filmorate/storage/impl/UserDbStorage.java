package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements Storage<User> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User getById(Long id) {
        String query = "SELECT * FROM filmorate.users WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, new UserMapper());
    }

    public User getByLogin(String login) {
        String query = "SELECT * FROM filmorate.users WHERE login = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{login}, new UserMapper());
    }

    @Override
    public User add(User entity) {
        jdbcTemplate.update("insert into filmorate.users (email, login, name, birthday) values (?, ?, ?, ?)",
                entity.getEmail(), entity.getLogin(), entity.getName(), entity.getBirthday());
        return getByLogin(entity.getLogin());
    }

    @Override
    public User update(User entity) {
        jdbcTemplate.update("update filmorate.users set email=?, login=?, name=?, birthday=? where id=?",
                entity.getEmail(), entity.getLogin(), entity.getName(), entity.getBirthday(), entity.getId());
        return entity;
    }

    @Override
    public Collection<User> getAll() {
        String query = "SELECT * FROM filmorate.users";
        return jdbcTemplate.query(query, new UserMapper());
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return User.builder()
                    .id(resultSet.getLong("id"))
                    .email(resultSet.getString("email"))
                    .login(resultSet.getString("login"))
                    .name(resultSet.getString("name"))
                    .birthday(resultSet.getObject("birthday", LocalDate.class))
                    .build();
        }
    }
}