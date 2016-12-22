package com.malinowski.blog.config;

import com.malinowski.blog.model.Categories;
import com.malinowski.blog.services.CategoriesFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by Jakub on 07.12.2016.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoriesFormatter categoriesFormatter;

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }

    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addFormatter(categoriesFormatter);
    }
}
