package com.malinowski.blog.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Jakub on 07.12.2016.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "User is already exist")
public class UserIsAlreadyExistException extends RuntimeException {
}
