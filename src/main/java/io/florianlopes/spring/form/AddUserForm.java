package io.florianlopes.spring.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AddUserForm {

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

    public AddUserForm(String name, List<String> firstNames, LocalDate birthDate) {
        this.name = name;
        this.firstNames = firstNames;
        this.birthDate = birthDate;
    }
}
