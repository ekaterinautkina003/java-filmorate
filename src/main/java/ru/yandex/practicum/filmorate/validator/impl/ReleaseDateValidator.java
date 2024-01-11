package ru.yandex.practicum.filmorate.validator.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validator.EntityFieldValidator;

import java.time.LocalDate;

@Component
public class ReleaseDateValidator implements EntityFieldValidator<LocalDate> {

  @Override
  public void validate(LocalDate field) {
    LocalDate date = LocalDate.of(1985, 12, 28);
    if (field.isBefore(date)) {
      throw new ValidationException("Release date can't be greater than 28.12.1985");
    }
  }
}
