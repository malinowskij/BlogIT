package com.malinowski.blog.web.controllers;

import com.malinowski.blog.Exceptions.PostNotFoundException;
import com.malinowski.blog.model.Categories;
import com.malinowski.blog.services.CategoriesFormatter;
import com.malinowski.blog.model.Post;
import com.malinowski.blog.model.User;
import com.malinowski.blog.repositories.interfaces.CategoryRepository;
import com.malinowski.blog.repositories.interfaces.PostRepository;
import com.malinowski.blog.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Jakub on 02.12.2016.
 */
@Controller
@RequestMapping("/posts")
public class PostsController {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public PostsController(PostRepository postRepository,
                           UserRepository userRepository,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/get/{id}")
    public String viewPost(@PathVariable Long id, Model model){
        if(!model.containsAttribute("post")){
            Post post = postRepository.findById(id);
            model.addAttribute("post", post);
            if(post == null) throw new PostNotFoundException();
        }
        model.addAttribute("latest5posts", postRepository.getLast(5));

        return "posts/get";
    }

    @GetMapping("/error")
    public String error(){
        return "notFound";
    }

    @Secured({"ROLE_ADMIN", "ROLE_REDACTOR"})
    @GetMapping("/create")
    public String showFormPostPage(Model model){
        model.addAttribute(new Post());
        model.addAttribute("categories", categoryRepository.allCategories());

        return "posts/create";
    }

    @Secured({"ROLE_ADMIN", "ROLE_REDACTOR"})
    @PostMapping("/create")
    public String processFormPost(@Valid Post post, Errors errors, RedirectAttributes model){
        if(errors.hasErrors()){
            return "posts/create";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.findByUsername(name);

        post.setAuthor(user);
        post.replaceImgsTags();
        post = postRepository.create(post);
        model.addFlashAttribute("post", post);
        model.addAttribute("id", post.getId());

        return "redirect:/posts/get/{id}";
    }

    @PostMapping("/search")
    public String searchPosts(@RequestParam String title, Model model){
        model.addAttribute("posts", postRepository.searchByTitle(title));
        return "posts/searchResult";
    }

    @GetMapping("/category/{category}")
    public String selectByCategory(@PathVariable Long category, Model model){
        model.addAttribute("posts", postRepository.selectByCategory(category));
        return "posts/searchResult";
    }
}
