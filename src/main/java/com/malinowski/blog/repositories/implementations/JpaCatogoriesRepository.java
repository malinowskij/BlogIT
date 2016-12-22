package com.malinowski.blog.repositories.implementations;

import com.malinowski.blog.model.Categories;
import com.malinowski.blog.repositories.interfaces.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Jakub on 07.12.2016.
 */
@Repository
public class JpaCatogoriesRepository implements CategoryRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Cacheable(value = "categories")
    @Override
    public List<Categories> allCategories() {
        Query query = entityManager.createQuery("SELECT c FROM Categories c");
        return query.getResultList();
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Override
    public Categories addCategory(Categories category) {
        return entityManager.merge(category);
    }

    @Override
    public boolean deleteCategory(Long id) {
        entityManager.remove(findCategoryById(id));
        if(findCategoryById(id) == null)
            return true;
        return false;
    }

    @Override
    public Categories findCategoryById(Long id) {
        return entityManager.find(Categories.class, id);
    }

    @Override
    public Categories findCategoryByName(String catogoryName) {
        return allCategories().stream()
                .filter(category -> category.getCategory().equals(catogoryName))
                .findFirst()
                .orElse(null);
    }
}
