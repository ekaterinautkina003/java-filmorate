package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.Service;
import ru.yandex.practicum.filmorate.storage.impl.MpaRatingDbStorage;

import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class MpaRatingService implements Service<MpaRating> {

    private final MpaRatingDbStorage mpaRatingDbStorage;

    @Override
    public MpaRating getById(Long id) {
        return mpaRatingDbStorage.getById(id);
    }

    @Override
    public MpaRating add(MpaRating mpaRating) {
        log.info("add: {}", mpaRating);
        return mpaRatingDbStorage.add(mpaRating);
    }

    @Override
    public MpaRating update(MpaRating mpaRating) {
        log.info("update: {}", mpaRating);
        return mpaRatingDbStorage.update(mpaRating);
    }

    @Override
    public Collection<MpaRating> getAll() {
        return mpaRatingDbStorage.getAll();
    }
}
