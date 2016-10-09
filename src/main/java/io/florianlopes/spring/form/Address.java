package io.florianlopes.spring.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// TODO headers
/**
 * Created by flopes on 18/08/2016.
 * <florian.lopes@outlook.com>
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Address {

    @NotNull
    @Min(1)
    private int streetNumber;

    @NotNull
    @Size(min = 2, max = 15)
    private String street;
}
