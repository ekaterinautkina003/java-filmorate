package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.FriendshipService;
import ru.yandex.practicum.filmorate.service.impl.UserService;
import ru.yandex.practicum.filmorate.validator.impl.LoginValidator;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final LoginValidator loginValidator;
    private final UserService userService;
    private final FriendshipService friendshipService;

    @PostMapping
    public ResponseEntity<User> add(@Valid @RequestBody User user) {
        loginValidator.validate(user.getLogin());
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return new ResponseEntity<>(userService.add(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        loginValidator.validate(user.getLogin());
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(
            @PathVariable("id") @PositiveOrZero Long userId,
            @PathVariable("friendId") @PositiveOrZero Long friendId
    ) {
        friendshipService.addFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(
            @PathVariable("id") Long userId,
            @PathVariable("friendId") Long friendId
    ) {
        friendshipService.deleteFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getAllFriends(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(friendshipService.getAllFriends(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getCrossFriends(
            @PathVariable("id") Long userId,
            @PathVariable("otherId") Long otherUserId
    ) {
        return new ResponseEntity<>(friendshipService.getCrossFriends(userId, otherUserId), HttpStatus.OK);
    }
}
