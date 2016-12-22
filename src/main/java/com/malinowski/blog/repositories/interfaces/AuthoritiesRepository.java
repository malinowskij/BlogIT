package com.malinowski.blog.repositories.interfaces;

import com.malinowski.blog.model.Authorities;
import com.malinowski.blog.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jakub on 04.12.2016.
 */
public interface AuthoritiesRepository {
    Authorities addUserAuthorities(User user);
    Authorities addAdminAuthorities(User user);

    @Transactional
    Authorities addRedactorAuthorities(User user);

    @Transactional
    void deleteAuthority(User user, String role);

    @Transactional
    void deleteAllAuthorities(User user);
}
