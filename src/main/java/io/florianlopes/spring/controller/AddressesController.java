package io.florianlopes.spring.controller;

import io.florianlopes.spring.form.AddAddressesForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by flopes on 18/08/2016.
 * <florian.lopes@outlook.com>
 */
@Controller
@RequestMapping("/addresses")
public class AddressesController {

    private static final String ADD_ADDRESSES_URL = "/addresses/add";
    private static final String ADD_ADDRESSES_FORM_ATTRIBUTE = "addAddressesForm";
    private static final String ADD_ADDRESSES_VIEW = "addAddresses";

    @GetMapping("/add")
    public String showAddAddressesForm(Model model) {
        model.addAttribute(ADD_ADDRESSES_FORM_ATTRIBUTE, new AddAddressesForm());
        return ADD_ADDRESSES_VIEW;
    }

    @PostMapping("/add")
    public String addAdresses(@Valid AddAddressesForm addAddressesForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return ADD_ADDRESSES_VIEW;
        } else {
            redirectAttributes.addFlashAttribute("flash", "Addresses added");
            return "redirect:" + ADD_ADDRESSES_URL;
        }
    }
}
