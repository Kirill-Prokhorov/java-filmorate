package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {

    private long id;

    @NotBlank
    private String name;

    @Size(max = 200, message = "Описание не может быть более более 200 символов")
    private String description;

    private LocalDate releaseDate;

    @NotNull
    @Positive
    private long duration;
}
