package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.FriendshipService;
import ru.yandex.practicum.filmorate.service.impl.UserService;
import ru.yandex.practicum.filmorate.validator.impl.LoginValidator;


import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final LoginValidator loginValidator;
    private final UserService userService;
    private final FriendshipService friendshipService;

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody User user) {
        try {
            loginValidator.validate(user.getLogin());
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            return new ResponseEntity<>(userService.add(user), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody User user) {
        try {
            loginValidator.validate(user.getLogin());
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
        } catch (EntityNotFoundException fg) {
            return new ResponseEntity<>(new ErrorResponse(fg.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable("id") Long userId, @PathVariable("friendId") Long friendId) {
        try {
            friendshipService.addFriend(userId, friendId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse(notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable("id") Long userId, @PathVariable("friendId") Long friendId) {
        try {
            friendshipService.deleteFriend(userId, friendId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse(notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<?> getAllFriends(@PathVariable("id") Long userId) {
        try {
            return new ResponseEntity<>(friendshipService.getAllFriends(userId), HttpStatus.OK);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse(notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<?> getCrossFriends(@PathVariable("id") Long userId, @PathVariable("otherId") Long otherUserId) {
        try {
            return new ResponseEntity<>(friendshipService.getCrossFriends(userId, otherUserId), HttpStatus.OK);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse(notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
