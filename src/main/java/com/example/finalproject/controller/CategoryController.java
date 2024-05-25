package com.example.finalproject.controller;

import com.example.finalproject.dto.request.category.AddCategoryRequest;
import com.example.finalproject.dto.response.category.CategoryResponse;
import com.example.finalproject.dto.response.order.OrderResponse;
import com.example.finalproject.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody AddCategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> readCategory(@PathVariable Integer id) {
        CategoryResponse categoryResponse = categoryService.findById(id);
        return ResponseEntity.ok(categoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
