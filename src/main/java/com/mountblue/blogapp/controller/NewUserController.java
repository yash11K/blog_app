package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.security.WebUser;
import com.mountblue.blogapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class NewUserController {
    private UserService userService;

    public NewUserController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/form")
    public String showRegistrationForm(Model model){

        model.addAttribute("webUser", new WebUser());
        return "register/registration-form";
    }

    @PostMapping("/newUser")
    public String registerNewUser(@Valid @ModelAttribute("webUser") WebUser webUser, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "register/registration-form";
        }
        if(userService.findUserByUserName(webUser.getUsername()).isPresent()){
            System.out.println("did this for username: " + userService.findUserByUserName(webUser.getUsername()).get().getUsername());
            model.addAttribute("registrationError", "UserName already Exists");
            return "register/registration-form";
        }

        userService.saveUser(webUser);
        System.out.println("created user successfully with username : " + webUser.getUsername());
        return "redirect:/login";
    }
}
