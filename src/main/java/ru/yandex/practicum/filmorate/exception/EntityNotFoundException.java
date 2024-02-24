package ru.yandex.practicum.filmorate.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE_NO_ID = "Entity with class '%s' is not exists";
    private static final String EXCEPTION_MESSAGE = "Entity with class '%s' is not exists by id: '%s'";
    private static final String EXCEPTION_MESSAGE_TWO_ID = "Entity with class '%s' is not exists by id: '%s' or id: '%s' ";

    public EntityNotFoundException(Class<?> clazz) {
        super(String.format(EXCEPTION_MESSAGE_NO_ID, clazz));
    }

    public EntityNotFoundException(Class<?> clazz, Long id) {
        super(String.format(EXCEPTION_MESSAGE, clazz, id));
    }

    public EntityNotFoundException(Class<?> clazz, Long id, Long secondId) {
        super(String.format(EXCEPTION_MESSAGE_TWO_ID, clazz, id, secondId));
    }
}
