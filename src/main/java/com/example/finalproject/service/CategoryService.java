package com.example.finalproject.service;

import com.example.finalproject.dto.request.category.AddCategoryRequest;
import com.example.finalproject.dto.response.category.CategoryResponse;

public interface CategoryService  {
    public CategoryResponse findById(Integer id);

   public CategoryResponse createCategory(AddCategoryRequest addCategoryRequest);
   public void deleteCategory(Integer id);
}
