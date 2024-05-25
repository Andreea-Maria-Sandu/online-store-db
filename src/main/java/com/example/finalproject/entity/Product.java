package com.example.finalproject.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString(exclude = "orderDetails")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private double productPrice;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY )
    //@JoinColumn(name = "product_id")
    private List<OrderDetails> orderDetails;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer productQuantity;
}
