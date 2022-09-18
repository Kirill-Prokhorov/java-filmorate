package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    private long id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    @NotNull
    private LocalDate birthday;

}
