package com.example.finalproject.testing;

import com.example.finalproject.dto.request.category.AddCategoryRequest;
import com.example.finalproject.dto.response.category.CategoryResponse;
import com.example.finalproject.entity.Category;
import com.example.finalproject.mapper.CategoryMapper;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.service.CategoryService;
import com.example.finalproject.service.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @Test
    public void createCategory(){
        Category category = new Category();
        category.setName("mancare");

        AddCategoryRequest addCategoryRequest = new AddCategoryRequest();
        addCategoryRequest.setName("mancare");
        when(categoryMapper.fromCategoryRequest(addCategoryRequest)).thenReturn(category);

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponse categoryResponse = new CategoryResponse();
        when(categoryMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        Assertions.assertEquals(categoryResponse,categoryService.createCategory(addCategoryRequest));

        verify(categoryRepository,times(1)).save(category);
    }
}
