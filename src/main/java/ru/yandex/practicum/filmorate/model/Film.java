package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class Film {
  private Long id;
  @NotEmpty
  private String name;
  @Size(max = 200)
  private String description;
  @PastOrPresent
  private LocalDate releaseDate;
  @PositiveOrZero
  private int duration;
  private Set<Long> likedUsers = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Film film = (Film) o;
    return duration == film.duration
            && Objects.equals(name, film.name)
            && Objects.equals(description, film.description)
            && Objects.equals(releaseDate, film.releaseDate)
            && Objects.equals(likedUsers, film.likedUsers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, releaseDate, duration, likedUsers);
  }
}
