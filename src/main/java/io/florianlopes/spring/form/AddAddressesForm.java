package io.florianlopes.spring.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by flopes on 25/08/2016.
 * <florian.lopes@outlook.com>
 */
@Getter
@Setter
@AllArgsConstructor
public class AddAddressesForm {

    @Min(0)
    private int userId;

    @NotEmpty
    @Valid
    private List<Address> addresses;

    public AddAddressesForm() {
    }
}
