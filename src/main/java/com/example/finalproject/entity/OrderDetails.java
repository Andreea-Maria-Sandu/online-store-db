package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "orderDetails")
@ToString(exclude = "order")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private int itemQuantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
