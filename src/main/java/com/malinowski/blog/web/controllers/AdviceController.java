package com.malinowski.blog.web.controllers;

import com.malinowski.blog.Exceptions.PostNotFoundException;
import com.malinowski.blog.Exceptions.UserIsAlreadyExistException;
import com.malinowski.blog.Exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by Jakub on 02.12.2016.
 */
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(PostNotFoundException.class)
    public String postNotFoundExceptionHandler(){
        return "redirect:/posts/error?post";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundExceptionHandler(){
        return "redirect:/users/error?user";
    }

    @ExceptionHandler(UserIsAlreadyExistException.class)
    public String userAlreadyExistExceptionHandler(){
        return "redirect:/users/register?exist";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundExceptionHandler(Exception ex){
        return "redirect:/error?elseErr";
    }
}
