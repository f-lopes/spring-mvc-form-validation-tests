package io.florianlopes.spring.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by flopes on 18/08/2016.
 * <florian.lopes@outlook.com>
 */
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @NotNull
    @Size(min = 1)
    private List<String> firstNames;

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @Valid
    private Address address;

    @NotNull
    @Size(min = 1)
    private String[] hobbies;

    @NotNull
    private Gender gender;

    public User(String name, Address address) {
        this.name = name;
        this.address = address;
    }

}
