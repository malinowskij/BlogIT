package com.malinowski.blog.repositories.interfaces;

import com.malinowski.blog.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jakub on 03.12.2016.
 */
public interface UserRepository {
    User create(User user);
    boolean delete(User user);
    boolean deleteById(Long id);
    void update(User user);
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
    boolean authenticate(String username, String passsword);
}
