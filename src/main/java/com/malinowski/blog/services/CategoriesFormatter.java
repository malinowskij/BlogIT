package com.malinowski.blog.services;

import com.malinowski.blog.model.Categories;
import com.malinowski.blog.repositories.interfaces.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Jakub on 13.12.2016.
 */
@Service
public class CategoriesFormatter implements Formatter<Categories> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Categories parse(String s, Locale locale) throws ParseException {
        Long id = Long.valueOf(s);
        return categoryRepository.findCategoryById(id);
    }

    @Override
    public String print(Categories categories, Locale locale) {
        return (categories != null ? categories.getId().toString() : "");
    }
}
