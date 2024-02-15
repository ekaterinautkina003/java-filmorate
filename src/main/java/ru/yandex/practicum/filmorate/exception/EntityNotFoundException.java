package ru.yandex.practicum.filmorate.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Entity with class '%s' is not exists by id: '%s'";

    public EntityNotFoundException(Class<?> clazz, Long id) {
        super(String.format(EXCEPTION_MESSAGE, clazz, id));
    }
}
