package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements Storage<User> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User getById(Long id) {
        String query = "SELECT * FROM filmorate.users WHERE id = ?";
        List<User> res = jdbcTemplate.query(query, new UserMapper(), id);
        if (res.isEmpty()) {
            throw new EntityNotFoundException(User.class, id);
        }
        return res.get(0);
    }

    public User getByLogin(String login) {
        String query = "SELECT * FROM filmorate.users WHERE login = ?";
        List<User> res = jdbcTemplate.query(query, new UserMapper(), login);
        if (res.isEmpty()) {
            throw new EntityNotFoundException(User.class);
        }
        return res.get(0);
    }

    @Override
    public User add(User entity) {
        jdbcTemplate.update("insert into filmorate.users" +
                        "    (email, login, name, birthday) " +
                        "     values (?, ?, ?, ?)",
                entity.getEmail(), entity.getLogin(), entity.getName(), entity.getBirthday());
        return getByLogin(entity.getLogin());
    }

    @Override
    public User update(User entity) {
        int res = jdbcTemplate.update("update filmorate.users " +
                        "   set email=?, login=?, name=?, birthday=? " +
                        "   where id=?",
                entity.getEmail(), entity.getLogin(), entity.getName(), entity.getBirthday(), entity.getId());
        if (res == 0) {
            throw new EntityNotFoundException(User.class, entity.getId());
        }
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