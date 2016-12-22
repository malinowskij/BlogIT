package com.malinowski.blog.repositories.implementations;
import com.malinowski.blog.Exceptions.UserIsAlreadyExistException;
import com.malinowski.blog.model.User;
import com.malinowski.blog.repositories.*;
import com.malinowski.blog.repositories.interfaces.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Queue;

/**
 * Created by Jakub on 03.12.2016.
 */
@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public User create(User user) {
        try{
            User usr = (User) entityManager.createQuery("SELECT u FROM User u WHERE u.username LIKE :userName")
                    .setParameter("userName", user.getUsername())
                    .getSingleResult();
        } catch (NoResultException ex){
            return entityManager.merge(user);
        }

        throw new UserIsAlreadyExistException();
    }

    @Transactional
    @Override
    public boolean delete(User user) {
        Long id = user.getId();
        entityManager.remove(user);
        if (findById(id) == null)
            return true;
        return false;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        User user = findById(id);
        entityManager.remove(user);
        if (findById(id) == null)
            return true;
        return false;
    }

    @Transactional
    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public User findByUsername(String username) {
        List<User> users = findAll();
        return users.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        Query query = entityManager.createQuery("select u from User u");
        return query.getResultList();
    }

    @Override
    public boolean authenticate(String username, String passsword) {
        return false;
    }
}