package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {
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
