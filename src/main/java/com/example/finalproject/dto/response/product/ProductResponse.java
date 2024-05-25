package com.example.finalproject.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String name;
    private Double price;
    private Integer quantity;
    private String categoryName;
}
