package io.florianlopes.spring.controller;

import io.florianlopes.spring.form.AddUserForm;
import io.florianlopes.spring.form.Address;
import io.florianlopes.spring.form.Gender;
import io.florianlopes.spring.propertyeditor.CustomLocalDatePropertyEditor;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Created by flopes on 18/08/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    private static final String ADD_USER_URL = "/users/add";
    private static final String ADD_USER_FORM_ATTRIBUTE = "addUserForm";

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        final UserController userController = new UserController();
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).setValidator(new LocalValidatorFactoryBean())
                .setConversionService(new DefaultFormattingConversionService())
                .build();
        MockMvcRequestBuilderUtils.registerPropertyEditor(LocalDate.class, new CustomLocalDatePropertyEditor(DATE_FORMAT_PATTERN));
    }

    @Test
    public void addUserFormWithNullUserAddress_shouldNotBeValidated() throws Exception {
        final AddUserForm addUserForm = AddUserForm.builder()
                .name("Doe")
                .hobbies(new String[]{"hobby1", "hobby2"})
                .firstNames(Arrays.asList(new String[]{"John", "Joe"}))
                .gender(Gender.MALE)
                .birthDate(LocalDate.now()).build();

        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm(ADD_USER_URL, addUserForm))
                .andExpect(MockMvcResultMatchers.model().attributeErrorCount(ADD_USER_FORM_ATTRIBUTE, 1))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors(ADD_USER_FORM_ATTRIBUTE, "address"));
    }

    @Test
    public void addUserFormWithMissingStreetName_shouldNotBeValidated() throws Exception {
        final AddUserForm addUserForm = AddUserForm.builder()
                .name("Doe")
                .address(new Address(5, null))
                .hobbies(new String[]{"hobby1", "hobby2"})
                .firstNames(Arrays.asList(new String[]{"John", "Joe"}))
                .gender(Gender.MALE)
                .birthDate(LocalDate.now()).build();

        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm(ADD_USER_URL, addUserForm))
                .andExpect(MockMvcResultMatchers.model().attributeErrorCount(ADD_USER_FORM_ATTRIBUTE, 1))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors(ADD_USER_FORM_ATTRIBUTE, "address.street"));
    }

    @Test
    public void addUserFormWithNullGender_shoudNotBeValidated() throws Exception {
        final AddUserForm addUserForm = AddUserForm.builder()
                .name("Doe")
                .address(new Address(5, "Some street"))
                .hobbies(new String[]{"hobby1", "hobby2"})
                .firstNames(Arrays.asList(new String[]{"John", "Joe"}))
                .birthDate(LocalDate.now()).build();

        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm(ADD_USER_URL, addUserForm))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors(ADD_USER_FORM_ATTRIBUTE))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors(ADD_USER_FORM_ATTRIBUTE, "gender"));
    }

    @Test
    public void addUserFormWithEmptyHobbies_shouldNotBeValidated() throws Exception {
        final AddUserForm addUserForm = AddUserForm.builder()
                .name("Doe")
                .address(new Address(5, "Some street"))
                .gender(Gender.MALE)
                .birthDate(LocalDate.now()).build();
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm(ADD_USER_URL, addUserForm))
                .andExpect(MockMvcResultMatchers.model().attributeErrorCount(ADD_USER_FORM_ATTRIBUTE, 2))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors(ADD_USER_FORM_ATTRIBUTE, "hobbies"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors(ADD_USER_FORM_ATTRIBUTE, "firstNames"));
    }

    @Test
    public void addUserFormWithAllFields_shouldBeValidated() throws Exception {
        final AddUserForm addUserForm = AddUserForm.builder()
                .name("Doe")
                .address(new Address(5, "Some street"))
                .birthDate(LocalDate.now())
                .hobbies(new String[]{"hobby1", "hobby2"})
                .gender(Gender.MALE)
                .firstNames(Arrays.asList(new String[]{"John", "Joe"})).build();
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm(ADD_USER_URL, addUserForm))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl(ADD_USER_URL));
    }
}