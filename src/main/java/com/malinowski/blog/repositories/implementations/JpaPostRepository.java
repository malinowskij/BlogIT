package com.malinowski.blog.repositories.implementations;

import com.malinowski.blog.model.Post;
import com.malinowski.blog.repositories.interfaces.PostRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jakub on 03.12.2016.
 */
@Repository
public class JpaPostRepository implements PostRepository {

    @PersistenceContext
    EntityManager entityManager;

    @CacheEvict(value = "posts", allEntries = true)
    @Transactional
    @Override
    public Post create(Post post) {
        return entityManager.merge(post);
    }

    @CacheEvict(value = "posts", allEntries = true)
    @Transactional
    @Override
    public boolean delete(Post post) {
        Long id = post.getId();
        entityManager.remove(post);
        if(findById(id) == null)
            return true;
        return false;
    }

    @CacheEvict(value = "posts", allEntries = true)
    @Transactional
    @Override
    public boolean deleteById(Long id) {
        Post post = findById(id);
        entityManager.remove(post);
        if(findById(id) == null)
            return true;
        return false;
    }

    @CacheEvict(value = "posts", allEntries = true)
    @Transactional
    @Override
    public Post update(Post post) {
        Post post2 = entityManager.find(Post.class, post.getId());
        post2.setBody(post.getBody());
        post2.setTitle(post.getTitle());
        return entityManager.merge(post2);
    }

    @Transactional
    @Override
    public Post findById(Long id) {
        return entityManager.find(Post.class, id);
    }

    @Cacheable(value = "posts")
    @Override
    public List<Post> allPosts() {
        Query query = entityManager.createQuery("select p from Post p");
        return query.getResultList();
    }

    @Cacheable(value = "posts")
    @Override
    public List<Post> getLast(int count) {
        List<Post> posts = allPosts();
        if(posts.size() < count) count = posts.size();
        return posts
                .stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .limit(count)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Post> searchByTitle(String tit){
        return entityManager.createQuery("SELECT p FROM Post p WHERE p.title LIKE :pattern")
                .setParameter("pattern", "%" + tit + "%")
                .getResultList();
    }

    @Transactional
    @Override
    public List<Post> selectByCategory(Long categ){
        return entityManager.createQuery("SELECT p FROM Post p WHERE p.category.id LIKE :categor")
                .setParameter("categor", categ)
                .getResultList();
    }
}
