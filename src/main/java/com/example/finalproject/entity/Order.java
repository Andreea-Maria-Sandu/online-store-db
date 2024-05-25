package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Orders")
@ToString(exclude = "user")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;   // UUID

    private String title;

    @CreationTimestamp
    private Date Date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus Status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetails;
}
