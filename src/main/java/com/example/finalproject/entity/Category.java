package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@ToString(exclude = "products")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public void addProduct(Product product)
    {
        this.products.add(product);
        product.setCategory(this);

    }

}