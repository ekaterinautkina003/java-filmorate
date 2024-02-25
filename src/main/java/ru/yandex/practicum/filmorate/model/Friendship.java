package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class Friendship {
    @Pattern(regexp = "^[a-z][a-zA-Z0-9]*$")
    private long user_id;
    @Pattern(regexp = "^[a-z][a-zA-Z0-9]*$")
    private long friend_id;
    private FrienshipStatus status;
}