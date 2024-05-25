package com.example.finalproject.dto.request.order;

import com.example.finalproject.dto.request.product.ProductDetails;
import com.example.finalproject.entity.Product;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AddOrderRequest {

    private String title;

    private List<ProductDetails> productDetails;

    private Integer userId;
}
