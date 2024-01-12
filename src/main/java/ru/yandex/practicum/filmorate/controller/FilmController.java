package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.impl.FilmService;
import ru.yandex.practicum.filmorate.validator.impl.ReleaseDateValidator;

import javax.validation.Valid;


@RestController
@RequestMapping("/film")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final ReleaseDateValidator releaseDateValidator;

    @PutMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody Film film) {
        try {
            releaseDateValidator.validate(film.getReleaseDate());
            return new ResponseEntity<>(filmService.add(film), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody Film film) {
        try {
            releaseDateValidator.validate(film.getReleaseDate());
            return new ResponseEntity<>(filmService.update(film), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(filmService.getAll(), HttpStatus.OK);
    }
}
