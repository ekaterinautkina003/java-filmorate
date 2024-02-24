package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FrienshipStatus;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendshipDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public void addFriend(Long userId, Long friendId) {
        jdbcTemplate.update("insert into filmorate.friendship (user_id, friend_id, status) values (?, ?, ?)",
                userId, friendId, FrienshipStatus.ACCCPETED.name());
    }

    public void deleteFriend(Long userId, Long friendId) {
        jdbcTemplate.update("delete from filmorate.friendship where (user_id=? and friend_id=?) or (friend_id=? and user_id=?)",
                userId, friendId, userId, friendId);
    }

    public List<User> getAllFriends(Long userId) {
        String query = "SELECT u.* FROM filmorate.users u " +
                "JOIN filmorate.friendship fs ON u.id = fs.friend_id " +
                "WHERE fs.user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new UserMapper());
    }

    public List<User> getCrossFriends(Long userId, Long otherUserId) {
        String query = "SELECT u.* FROM filmorate.users u " +
                "JOIN filmorate.friendship fs1 ON u.id = fs1.friend_id " +
                "JOIN filmorate.friendship fs2 ON u.id = fs2.friend_id " +
                "WHERE fs1.user_id = ? AND fs2.user_id = ? " +
                "AND fs1.status = ? AND fs2.status = ?";
        return jdbcTemplate.query(query, new Object[]{userId, otherUserId, FrienshipStatus.ACCCPETED.name(), FrienshipStatus.ACCCPETED.name()},
                new UserMapper());
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
