package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Film {

    private long id;

    @NotBlank
    private String name;

    @Size(max = 200, message = "Описание не может быть более более 200 символов")
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private long duration;

    private Set<Long> likesByUserIds = new HashSet<>();
    private Set<Genre> genres;
    private Mpa mpa;

    public Film(long id, String name, String description, LocalDate releaseDate, long duration, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }

}
