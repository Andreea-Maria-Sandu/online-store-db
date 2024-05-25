package com.example.finalproject.dto.request.order;

import com.example.finalproject.dto.request.product.ProductDetails;
import com.example.finalproject.entity.OrderDetails;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
public class UpdateOrderRequest {

    private List<ProductDetails> productDetails;
    @CreationTimestamp
    private Date updateOrderDate;
}
