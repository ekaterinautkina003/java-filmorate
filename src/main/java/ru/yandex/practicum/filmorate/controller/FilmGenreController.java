package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.service.impl.FilmGenreService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class FilmGenreController {

    private final FilmGenreService filmGenreService;

    @GetMapping("/{id}")
    public ResponseEntity<FilmGenre> getById(@PathVariable("id") Long id) {
        FilmGenre rating = filmGenreService.getById(id);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<FilmGenre>> getAll() {
        return new ResponseEntity<>(filmGenreService.getAll(), HttpStatus.OK);
    }
}
