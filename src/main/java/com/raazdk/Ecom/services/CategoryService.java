package com.raazdk.Ecom.services;



import com.raazdk.Ecom.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();

    String addCategory(Category category);

    String updateCategory(Category categoryToUpdate, Long catId);

    String deleteCategory(Long categoryId);
}
