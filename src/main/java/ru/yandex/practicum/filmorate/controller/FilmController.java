package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.impl.FilmService;
import ru.yandex.practicum.filmorate.validator.impl.ReleaseDateValidator;

import javax.validation.Valid;


@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

  private final FilmService filmService;
  private final ReleaseDateValidator releaseDateValidator;

  @PostMapping
  public ResponseEntity<?> add(@Valid @RequestBody Film film) {
    try {
      releaseDateValidator.validate(film.getReleaseDate());
      return new ResponseEntity<>(filmService.add(film), HttpStatus.OK);
    } catch (ValidationException e) {
      return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping
  public ResponseEntity<?> update(@Valid @RequestBody Film film) {
    try {
      releaseDateValidator.validate(film.getReleaseDate());
      return new ResponseEntity<>(filmService.update(film), HttpStatus.OK);
    } catch (ValidationException e) {
      return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping
  public ResponseEntity<?> getAll() {
    return new ResponseEntity<>(filmService.getAll(), HttpStatus.OK);
  }
}
