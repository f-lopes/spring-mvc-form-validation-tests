package io.florianlopes.spring.controller;

import io.florianlopes.spring.form.AddUserForm;
import io.florianlopes.spring.propertyeditor.CustomLocalDatePropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * Created by flopes on 18/08/2016.
 * <florian.lopes@outlook.com>
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static final String ADD_USER_URL = "/users/add";
    private static final String ADD_USER_FORM_ATTRIBUTE = "addUserForm";
    private static final String ADD_USER_VIEW = "addUser";

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(LocalDate.class, new CustomLocalDatePropertyEditor("dd/MM/yyyy"));
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute(ADD_USER_FORM_ATTRIBUTE, new AddUserForm());
        return ADD_USER_VIEW;
    }

    @PostMapping("/add")
    public String addUser(@Valid AddUserForm addUserForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return ADD_USER_VIEW;
        } else {
            redirectAttributes.addFlashAttribute("flash", "User added");
            return "redirect:" + ADD_USER_URL;
        }
    }
}
