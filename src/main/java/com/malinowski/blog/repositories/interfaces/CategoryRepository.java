package com.malinowski.blog.repositories.interfaces;

import com.malinowski.blog.model.Categories;

import java.util.List;

/**
 * Created by Jakub on 07.12.2016.
 */
public interface CategoryRepository {
    List<Categories> allCategories();
    Categories addCategory(Categories category);
    boolean deleteCategory(Long id);
    Categories findCategoryById(Long id);
    Categories findCategoryByName(String catogoryName);
}
