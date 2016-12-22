package com.malinowski.blog.web.controllers;

import com.malinowski.blog.model.Authorities;
import com.malinowski.blog.model.Post;
import com.malinowski.blog.model.User;
import com.malinowski.blog.repositories.interfaces.AuthoritiesRepository;
import com.malinowski.blog.repositories.interfaces.CategoryRepository;
import com.malinowski.blog.repositories.interfaces.PostRepository;
import com.malinowski.blog.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
 * Created by Jakub on 08.12.2016.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    public AdminController(PostRepository postRepository,
                           UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           AuthoritiesRepository authoritiesRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String getAdminPanel(){
        return "/admin";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/posts/delete/{id}")
    public String deletePostById(@PathVariable Long id){
        if(postRepository.deleteById(id)){
            return "redirect:/";
        }
        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/posts/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model){
        model.addAttribute("post", postRepository.findById(id));

        return "posts/edit";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/posts/edit")
    public String processEditForm(@Valid Post post, Errors errors, RedirectAttributes model){
        if(errors.hasErrors()){
            return "posts/edit";
        }
        post = postRepository.update(post);
        post.replaceImgsTags();
        model.addFlashAttribute("post", post);
        model.addAttribute("id", post.getId());

        return "redirect:/posts/get/{id}";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);

        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/auth/r_redact/{id}")
    public String addRedactRole(@PathVariable Long id, Model model){
        User user = userRepository.findById(id);
        Authorities authorities = new Authorities(user, "ROLE_REDACTOR");
        if(!user.getRoles().contains(authorities))
            authoritiesRepository.addRedactorAuthorities(user);

        model.addAttribute("id", id);

        return "redirect:/users/get/{id}";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/auth/rem_redact/{id}")
    public String deleteRedactRole(@PathVariable Long id, Model model){
        User user = userRepository.findById(id);
        Authorities authorities = new Authorities(user, "ROLE_REDACTOR");
        if(user.getRoles().contains(authorities))
            authoritiesRepository.deleteAuthority(user, "ROLE_REDACTOR");

        model.addAttribute("id", id);

        return "redirect:/users/get/{id}";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, Model model){
        User user = userRepository.findById(id);
        authoritiesRepository.deleteAllAuthorities(user);
        userRepository.delete(user);
        return "redirect:/users";
    }

}
