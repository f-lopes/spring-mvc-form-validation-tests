package io.florianlopes.spring.controller;

import io.florianlopes.spring.form.AddAddressesForm;
import io.florianlopes.spring.form.Address;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by flopes on 25/08/2016.
 * <florian.lopes@outlook.com>
 */
public class AddressesControllerTest {

    private static final String ADD_ADDRESSES_URL = "/addresses/add";
    private static final String ADD_ADDRESSES_FORM_ATTRIBUTE = "addAddressesForm";

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        final AddressesController addressesController = new AddressesController();
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressesController).setValidator(new LocalValidatorFactoryBean())
                .setConversionService(new DefaultFormattingConversionService())
                .build();
    }

    @Test
    public void addAddressesFormWithEmptyAddresses_shouldNotBeValidated() throws Exception {
        final AddAddressesForm addAddressesForm = new AddAddressesForm(1, Collections.emptyList());
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm(ADD_ADDRESSES_URL, addAddressesForm))
                .andExpect(MockMvcResultMatchers.model().attributeErrorCount(ADD_ADDRESSES_FORM_ATTRIBUTE, 1))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors(ADD_ADDRESSES_FORM_ATTRIBUTE, "addresses"));
    }

    @Test
    public void addAddressesFormWithEmptyStreeName_shouldNotBeValidated() throws Exception {
        final AddAddressesForm addAddressesForm = new AddAddressesForm(1,
                Arrays.asList(new Address(1, ""), new Address(2, "secondStreet")));
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm(ADD_ADDRESSES_URL, addAddressesForm))
                .andExpect(MockMvcResultMatchers.model().attributeErrorCount(ADD_ADDRESSES_FORM_ATTRIBUTE, 1));
    }

    @Test
    public void addAddressesFormWithAddresses_shouldBeValidated() throws Exception {
        final AddAddressesForm addAddressesForm = new AddAddressesForm(1,
                Arrays.asList(new Address(1, "firstStreet"), new Address(2, "secondStreet")));
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm(ADD_ADDRESSES_URL, addAddressesForm))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl(ADD_ADDRESSES_URL));
    }
}
