package ru.yandex.practicum.filmorate.controller.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.yandex.practicum.filmorate.controller.ControllerTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest extends ControllerTest {

  private static final String FILM_REQUEST_MAPPING = "/films";

  private static Stream<Arguments> releaseDateMethodSource() {
    return Stream.of(
            Arguments.of(LocalDate.now().minusYears(5), status().is2xxSuccessful()),
            Arguments.of(LocalDate.of(1894, 12, 28), status().is4xxClientError()),
            Arguments.of(LocalDate.of(2025, 1, 28), status().is4xxClientError())
    );
  }

  @Autowired
  private MockMvc mockMvc;

  @Test
  @Order(1)
  @SneakyThrows
  void add() {
    Film film = new Film();
    film.setId(1L);
    film.setDescription("description");
    film.setDuration(200);
    film.setName("name");
    film.setReleaseDate(LocalDate.now().minusYears(1));
    String jsonFilm = gson.toJson(film);

    mockMvc.perform(put(FILM_REQUEST_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON).content(jsonFilm))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(jsonFilm)));
  }

  @Test
  @Order(2)
  @SneakyThrows
  void update() {
    Film film = new Film();
    film.setId(1L);
    film.setDescription("NEW DESCRIPTION");
    film.setDuration(200);
    film.setName("name");
    film.setReleaseDate(LocalDate.now().minusYears(1));
    String jsonFilm = gson.toJson(film);

    mockMvc.perform(post(FILM_REQUEST_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON).content(jsonFilm))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(jsonFilm)));
  }

  @Test
  @Order(3)
  @SneakyThrows
  void getAll() {
    mockMvc.perform(get(FILM_REQUEST_MAPPING))
            .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void validateName() {
    Film film = new Film();
    film.setId(1L);
    film.setDescription("description");
    film.setDuration(200);
    film.setName("");
    film.setReleaseDate(LocalDate.now().minusYears(1));
    String jsonFilm = gson.toJson(film);

    mockMvc.perform(put(FILM_REQUEST_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON).content(jsonFilm))
            .andExpect(status().is4xxClientError());
  }

  @Test
  @SneakyThrows
  void validateDescription() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 205; i++) {
      sb.append("d");
    }
    String description = sb.toString();

    Film film = new Film();
    film.setId(1L);
    film.setDescription(description);
    film.setDuration(200);
    film.setName("");
    film.setReleaseDate(LocalDate.now().minusYears(1));
    String jsonFilm = gson.toJson(film);

    mockMvc.perform(put(FILM_REQUEST_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON).content(jsonFilm))
            .andExpect(status().is4xxClientError());
  }

  @SneakyThrows
  @ParameterizedTest
  @MethodSource("releaseDateMethodSource")
  void validateReleaseDate(LocalDate releaseDate, ResultMatcher resultMatcher) {
    Film film = new Film();
    film.setId(1L);
    film.setDescription("description");
    film.setDuration(200);
    film.setName("432143");
    film.setReleaseDate(releaseDate);
    String jsonFilm = gson.toJson(film);

    mockMvc.perform(put(FILM_REQUEST_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON).content(jsonFilm))
            .andExpect(resultMatcher);
  }

  @Test
  @SneakyThrows
  void validateDuration() {
    Film film = new Film();
    film.setId(1L);
    film.setDescription("description");
    film.setDuration(-1);
    film.setName("432143");
    film.setReleaseDate(LocalDate.now().minusYears(1));
    String jsonFilm = gson.toJson(film);

    mockMvc.perform(put(FILM_REQUEST_MAPPING)
                    .contentType(MediaType.APPLICATION_JSON).content(jsonFilm))
            .andExpect(status().is4xxClientError());
  }
}
