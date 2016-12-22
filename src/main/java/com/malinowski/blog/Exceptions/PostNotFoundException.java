package com.malinowski.blog.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Jakub on 02.12.2016.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "not found post")
public class PostNotFoundException extends RuntimeException{

}
