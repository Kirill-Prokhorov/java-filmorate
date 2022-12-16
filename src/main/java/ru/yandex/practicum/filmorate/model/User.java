package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @NotNull
    private long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String login;

    @NotNull
    private String name;

    @PastOrPresent
    @NotNull
    private LocalDate birthday;
    private Set<Long> friendList = new HashSet<>();

    public User(Long id, String login, String email, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
