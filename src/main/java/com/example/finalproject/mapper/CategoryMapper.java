package com.example.finalproject.mapper;

import com.example.finalproject.dto.request.category.AddCategoryRequest;
import com.example.finalproject.dto.response.category.CategoryResponse;
import com.example.finalproject.entity.Category;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category fromCategoryRequest(AddCategoryRequest categoryRequest) {

        if (categoryRequest == null) {
            return null;
        }

        Category category = new Category();

        category.setName(categoryRequest.getName());

        return category;
    }

    public CategoryResponse toCategoryResponse(Category category) {

        if (category == null) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();


        categoryResponse.setName(category.getName());


        return categoryResponse;
    }
}
