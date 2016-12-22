package com.malinowski.blog.web.controllers;

import com.malinowski.blog.Exceptions.PostNotFoundException;
import com.malinowski.blog.Exceptions.UserNotFoundException;
import com.malinowski.blog.model.Authorities;
import com.malinowski.blog.model.User;
import com.malinowski.blog.repositories.interfaces.AuthoritiesRepository;
import com.malinowski.blog.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Jakub on 03.12.2016.
 */
@Controller
@RequestMapping("/users")
public class UserController {


    private PasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    public UserController(UserRepository userRepository, AuthoritiesRepository authoritiesRepository,
                          PasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String showAllUsers(Model model){
        model.addAttribute("userList", userRepository.findAll());

        return "users/view";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());

        return "users/register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid User user, Errors errors, RedirectAttributes model){
        if(errors.hasErrors()){
            return "users/register";
        }

        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = userRepository.create(user);
        authoritiesRepository.addUserAuthorities(user);

        model.addFlashAttribute("user", user);
        model.addAttribute("id", user.getId());

        return "redirect:/users/get/{id}";
    }

    @GetMapping("/get/{id}")
    public String showUserById(@PathVariable Long id, Model model){
        if(!model.containsAttribute("user")){
            User usr = userRepository.findById(id);

            if(usr == null) throw new UserNotFoundException();

            model.addAttribute(userRepository.findById(id));
        }

        return "users/get";
    }

    @GetMapping("/error")
    public String error(){
        return "notFound";
    }
}
