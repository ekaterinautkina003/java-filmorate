package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.UserService;
import ru.yandex.practicum.filmorate.validator.impl.LoginValidator;

import javax.validation.Valid;
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final LoginValidator loginValidator;

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
    } catch (ValidationException e) {
      return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping
  public ResponseEntity<?> getAll() {
    return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
  }
}
