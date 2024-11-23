package com.raazdk.Ecom.services;


import com.raazdk.Ecom.models.Category;
import com.raazdk.Ecom.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceIml  implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    List<Category> categories = new ArrayList<>();
    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String addCategory(Category category) {
        categoryRepository.save(category);
        return "Added item";
    }

    @Override
    public String updateCategory(Category categoryToUpdate, Long catId) {
        Optional<Category> savedCategory = categoryRepository.findById(catId);
        Category category = savedCategory.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Item not found!!"));
        categoryRepository.save(categoryToUpdate);
        return "CategoryId"+catId+" Updated to "+categoryToUpdate.toString();
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> savedCategory = categoryRepository.findById(categoryId);
        Category category = savedCategory.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Item not found!!"));
        categoryRepository.delete(category);
        return "CategoryId"+categoryId+" Deleted successfully!!";
    }
}
