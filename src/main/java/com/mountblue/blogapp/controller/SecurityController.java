package com.mountblue.blogapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/loginPage")
    public String showLoginPage(){
        return "login";
    }

}
