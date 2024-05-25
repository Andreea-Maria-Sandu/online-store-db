package com.example.finalproject.mapper;

import com.example.finalproject.dto.request.category.AddCategoryRequest;
import com.example.finalproject.dto.request.product.AddProductRequest;
import com.example.finalproject.dto.request.product.UpdateProductRequest;
import com.example.finalproject.dto.response.product.ProductResponse;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final CategoryMapper categoryMapper;
    private CategoryRepository categoryRepository;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public ProductResponse fromProductEntity(Product product){
        ProductResponse productResponse=new ProductResponse();
        productResponse.setName(product.getProductName());
        productResponse.setPrice(product.getProductPrice());
        productResponse.setQuantity(product.getProductQuantity());
        if(product.getCategory() != null)
            productResponse.setCategoryName(product.getCategory().getName());
        //productResponse.setCategory(product.getCategory());
        return productResponse;
    }

    public Product fromRequestProduct(AddProductRequest addProductRequest){
        Product product = new Product();

        product.setProductName(addProductRequest.getName());
        product.setProductPrice(addProductRequest.getPrice());
        product.setProductQuantity(addProductRequest.getQuantity());
//        AddCategoryRequest addCategoryRequest2 = new AddCategoryRequest();
//        addCategoryRequest2.setName(addProductRequest.getName());
//        product.setCategory(categoryMapper.fromCategoryRequest(addCategoryRequest2));
        return product;
    }

    public Product updateProductRequest(Product product, UpdateProductRequest updateProductRequest){
        product.setProductPrice(updateProductRequest.getPrice());
        product.setProductQuantity(updateProductRequest.getQuantity());

        return product;
    }
}
