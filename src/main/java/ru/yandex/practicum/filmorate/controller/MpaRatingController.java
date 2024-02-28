package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.impl.MpaRatingService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaRatingController {

    private final MpaRatingService mpaRatingService;

    @GetMapping("/{id}")
    public ResponseEntity<MpaRating> getById(@PathVariable("id") Long id) {
        MpaRating rating = mpaRatingService.getById(id);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<MpaRating>> getAll() {
        return new ResponseEntity<>(mpaRatingService.getAll(), HttpStatus.OK);
    }
}
