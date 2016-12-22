package com.malinowski.blog.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jakub on 04.12.2016.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginForm(){
        return "login";
    }
}
