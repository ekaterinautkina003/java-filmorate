package ru.yandex.practicum.filmorate.validator.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validator.EntityFieldValidator;

@Component
public class LoginValidator implements EntityFieldValidator<String> {
  @Override
  public void validate(String field) {
    String[] parts = field.split(" ");
    if (parts.length > 1) {
      throw new ValidationException("Login should not contains spaces");
    }
  }
}
