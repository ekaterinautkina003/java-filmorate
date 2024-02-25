package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friendship {
    private long userId;
    private long friendId;
    private FrienshipStatus status;
}