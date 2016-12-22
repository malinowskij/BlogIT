package com.malinowski.blog.services.interfaces;

/**
 * Created by Jakub on 04.12.2016.
 */
public interface SecurityService {
    String findLoggedInUsername();
    void autologin(String username, String password);
}
