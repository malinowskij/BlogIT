package com.malinowski.blog.web.controllers;

import com.malinowski.blog.model.Post;
import com.malinowski.blog.repositories.interfaces.PagingPostRepository;
import com.malinowski.blog.repositories.interfaces.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jakub on 02.12.2016.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private PostRepository postRepository;
    @Autowired
    private PagingPostRepository pagingPostRepository;
    @Autowired
    public HomeController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public String home(Model model, Pageable pageable){
        model.addAttribute("posts5", postRepository.getLast(5));

        List<Post> latest3posts = postRepository.getLast(5).stream()
                .limit(3).collect(Collectors.toList());

        model.addAttribute("posts3", latest3posts);
        /*Page<Post> posts = pagingPostRepository.findAll(pageable);
        model.addAttribute("post", posts);*/
        return "home";
    }

    @GetMapping("error")
    public String error(){
        return "notFound";
    }
}
