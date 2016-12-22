package com.malinowski.blog.repositories.interfaces;

import com.malinowski.blog.model.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jakub on 03.12.2016.
 */
public interface PostRepository {
    Post create(Post post);
    boolean delete(Post post);
    boolean deleteById(Long id);
    Post update(Post post);
    Post findById(Long id);
    List<Post> allPosts();
    List<Post> getLast(int count);

    @Transactional
    List<Post> searchByTitle(String title);

    @Transactional
    List<Post> selectByCategory(Long categ);
}
