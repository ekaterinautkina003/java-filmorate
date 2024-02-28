package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.impl.FilmLikesService;
import ru.yandex.practicum.filmorate.service.impl.FilmService;
import ru.yandex.practicum.filmorate.validator.impl.ReleaseDateValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Validated
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final FilmLikesService filmLikesService;
    private final ReleaseDateValidator releaseDateValidator;

    @PostMapping
    public ResponseEntity<Film> add(@Valid @RequestBody Film film) {
        releaseDateValidator.validate(film.getReleaseDate());
        return new ResponseEntity<>(filmService.add(film), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {
        releaseDateValidator.validate(film.getReleaseDate());
        return new ResponseEntity<>(filmService.update(film), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> getAll() {
        return new ResponseEntity<>(filmService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(filmService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> addLike(@PathVariable("id") Long filmId, @PathVariable("userId") Long userId) {
        filmLikesService.addLike(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> removeLike(@PathVariable("id") Long filmId, @PathVariable("userId") Long userId) {
        filmLikesService.removeLike(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> popular(
            @RequestParam(name = "count", required = false, defaultValue = "10") Integer count) {
        return new ResponseEntity<>(filmLikesService.getPopular(count), HttpStatus.OK);
    }
}
