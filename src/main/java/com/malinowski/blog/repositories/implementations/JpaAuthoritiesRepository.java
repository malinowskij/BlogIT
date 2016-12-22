package com.malinowski.blog.repositories.implementations;

import com.malinowski.blog.model.Authorities;
import com.malinowski.blog.model.User;
import com.malinowski.blog.repositories.interfaces.AuthoritiesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Jakub on 04.12.2016.
 */
@Repository
public class JpaAuthoritiesRepository implements AuthoritiesRepository {
    private final static String ROLE_USER = "ROLE_USER";
    private final static String ROLE_ADMIN = "ROLE_ADMIN";
    private final static String ROLE_REDACTOR = "ROLE_REDACTOR";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Authorities addUserAuthorities(User user) {
        return entityManager.merge(new Authorities(user, ROLE_USER));
    }

    @Transactional
    @Override
    public Authorities addAdminAuthorities(User user) {
        return entityManager.merge(new Authorities(user, ROLE_ADMIN));
    }

    @Transactional
    @Override
    public Authorities addRedactorAuthorities(User user){
        return entityManager.merge(new Authorities(user, ROLE_REDACTOR));
    }

    @Transactional
    @Override
    public void deleteAuthority(User user, String role){
        Authorities authorities = (Authorities) entityManager.
                createQuery("SELECT a FROM Authorities a WHERE a.authority LIKE :authority AND a.user LIKE :username")
                .setParameter("authority", role)
                .setParameter("username", user)
                .getSingleResult();
        entityManager.remove(authorities);
    }

    @Transactional
    @Override
    public void deleteAllAuthorities(User user){
        Query query = entityManager.createQuery("DELETE FROM Authorities a WHERE a.user LIKE :username")
                .setParameter("username", user);
        query.executeUpdate();
    }
}
