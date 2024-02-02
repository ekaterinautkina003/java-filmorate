package ru.yandex.practicum.filmorate.validator;

public interface EntityFieldValidator<T> {

  void validate(T field);
}
