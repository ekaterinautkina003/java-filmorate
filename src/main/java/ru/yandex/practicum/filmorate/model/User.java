package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
  @PositiveOrZero
  private Long id;
  @Email
  @NotEmpty
  private String email;
  @NotEmpty
  private String login;
  private String name;
  @PastOrPresent
  private LocalDate birthday;
}
