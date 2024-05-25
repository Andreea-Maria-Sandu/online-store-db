package com.example.finalproject.mapper;

import com.example.finalproject.dto.request.order.AddOrderRequest;
import com.example.finalproject.dto.request.order.UpdateOrderRequest;
import com.example.finalproject.dto.request.product.ProductDetails;
import com.example.finalproject.dto.response.order.OrderResponse;
import com.example.finalproject.entity.Order;
import com.example.finalproject.entity.OrderDetails;
import com.example.finalproject.entity.OrderStatus;
import com.example.finalproject.entity.Product;
import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderResponse orderFromOrderEntity (Order order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setTitle(order.getTitle());
        orderResponse.setStatus(order.getStatus().name());

        List<ProductDetails> productDetails= new ArrayList<>();
        order.getOrderDetails().forEach(orderDetails->productDetails.add(new ProductDetails(orderDetails.getProduct().getProductName(),orderDetails.getItemQuantity())));
        orderResponse.setProductName(productDetails);

        return  orderResponse;
    }

    public Order orderFromAddOrderRequest(AddOrderRequest addOrderRequest){
        Order order = new Order();
        order.setTitle(addOrderRequest.getTitle());
        order.setStatus(OrderStatus.PLACED);
        //client
        return order;
    }

    public Order updateOrderRequest(Order order, UpdateOrderRequest updateOrderRequest){
       // order.setOrderDetails(updateOrderRequest.getOrderDetails());
       // order.setDate(new Date(String.valueOf(LocalDate.now())));
        return order;
    }

}
