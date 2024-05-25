package com.example.finalproject.service;

import com.example.finalproject.dto.request.product.AddProductRequest;
import com.example.finalproject.dto.request.product.UpdateProductRequest;
import com.example.finalproject.dto.response.product.ProductResponse;


import java.util.List;


public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Integer id);

    ProductResponse addProduct(AddProductRequest addProductRequest);

    void updateProduct(Integer id, UpdateProductRequest updateProductRequest);

    void deleteProductById(Integer id);

}
