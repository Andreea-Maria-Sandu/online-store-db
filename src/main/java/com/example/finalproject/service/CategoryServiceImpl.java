package com.example.finalproject.service;

import com.example.finalproject.dto.request.category.AddCategoryRequest;
import com.example.finalproject.dto.response.category.CategoryResponse;
import com.example.finalproject.entity.Category;
import com.example.finalproject.exception.category.CategoryNotFoundException;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            Category category2 = category.get();
            CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category2);
            return categoryResponse;
        }else throw new CategoryNotFoundException("Category not found!");
    }
    @Override
    public CategoryResponse createCategory(AddCategoryRequest categoryRequest) {

        Category category = categoryMapper.fromCategoryRequest(categoryRequest);


        Category savedCategory = categoryRepository.save(category);


        return categoryMapper.toCategoryResponse(savedCategory);
    }
    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException( "Category not found with id " + id));
        categoryRepository.delete(category);
    }

}
