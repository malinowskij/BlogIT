package com.malinowski.blog.repositories.interfaces;

import com.malinowski.blog.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by Jakub on 16.12.2016.
 */
public interface PagingPostRepository extends PagingAndSortingRepository<Post, Integer> {
}
