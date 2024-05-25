package com.example.finalproject.service;

import com.example.finalproject.dto.request.order.AddOrderRequest;
import com.example.finalproject.dto.request.order.UpdateOrderRequest;
import com.example.finalproject.dto.request.product.ProductDetails;
import com.example.finalproject.dto.response.order.OrderResponse;
import com.example.finalproject.entity.Order;
import com.example.finalproject.entity.OrderDetails;
import com.example.finalproject.entity.Product;
import com.example.finalproject.entity.User;
import com.example.finalproject.exception.error.ApiError;
import com.example.finalproject.exception.order.OrderNotFoundException;
import com.example.finalproject.exception.user.UserNotFoundException;
import com.example.finalproject.mapper.OrderMapper;
import com.example.finalproject.repository.OrderRepository;
import com.example.finalproject.repository.ProductRepository;
import com.example.finalproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = orders.stream().map(orderMapper::orderFromOrderEntity).collect(Collectors.toList());
        return orderResponses;
    }

    @Override
    public OrderResponse getOrderById(Integer id) {
        Optional<Order>optionalOrder=orderRepository.findById(id);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            OrderResponse orderResponse=orderMapper.orderFromOrderEntity(order);
            return orderResponse;
        }else {
            throw new OrderNotFoundException("Order not found");
        }
    }

    @Override
    @Transactional
    public OrderResponse createOrder(AddOrderRequest addOrderRequest) {
        Order order = orderMapper.orderFromAddOrderRequest(addOrderRequest);
        Optional<User> user = userRepository.findById(addOrderRequest.getUserId());
        if (user.isEmpty()){
            throw new UserNotFoundException("user not found");
        }

        List<ProductDetails> productsDetails = addOrderRequest.getProductDetails();


        List<String> productsName=new ArrayList<>();
        productsDetails.forEach(productDetails -> productsName.add(productDetails.getProductName()));

        List<Product> orderProducts = productRepository.findAll().stream()
                .filter(product -> productsName.contains(product.getProductName())).collect(Collectors.toList());

        List<OrderDetails> orderDetailsList = new ArrayList<>();

        for (ProductDetails productDetails : productsDetails) {
            Product product = orderProducts.stream()
                    .filter(p -> p.getProductName().equals(productDetails.getProductName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productDetails));

            OrderDetails orderDetails = new OrderDetails();

            orderDetails.setProduct(product);

            if(product.getProductQuantity()>=productDetails.getQuantity()) {
                orderDetails.setItemQuantity(productDetails.getQuantity());
                product.setProductQuantity(product.getProductQuantity()-productDetails.getQuantity());
            }
            else throw new RuntimeException("Out of stock!");

            orderDetails.setOrder(order);

            orderDetailsList.add(orderDetails);
        }

        order.setOrderDetails(orderDetailsList);
        order.setUser(user.get());

        orderRepository.save(order);

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setTitle(order.getTitle());
        orderResponse.setStatus(order.getStatus().name());
        orderResponse.setProductName(productsDetails);


        return orderResponse;
    }

    @Override
    public OrderResponse updateOrder(Integer id, UpdateOrderRequest updateOrderRequest) {

        try{
            Order existingOrder = orderRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));


            orderMapper.updateOrderRequest(existingOrder, updateOrderRequest);

            existingOrder.getOrderDetails().clear();

            List<String> productsName = updateOrderRequest.getProductDetails().stream()
                    .map(ProductDetails::getProductName).collect(Collectors.toList());

            List<Product> orderProducts = productRepository.findAll().stream()
                    .filter(product -> productsName.contains(product.getProductName()))
                    .collect(Collectors.toList());
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            for(ProductDetails productDetails : updateOrderRequest.getProductDetails()) {
                String productName = productDetails.getProductName();
                int quantity = productDetails.getQuantity();

                Product product = orderProducts.stream()
                        .filter(p -> p.getProductName().equals(productName))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                OrderDetails orderDetails1 = new OrderDetails();

                orderDetails1.setProduct(product);
                if(product.getProductQuantity()>=productDetails.getQuantity()) {
                    orderDetails1.setItemQuantity(productDetails.getQuantity());
                    product.setProductQuantity(product.getProductQuantity()-productDetails.getQuantity());
                }
                else throw new RuntimeException("Out of stock!");
                orderDetails1.setItemQuantity(quantity);
                orderDetails1.setOrder(existingOrder);

                orderDetailsList.add(orderDetails1);

            }

            existingOrder.setOrderDetails(orderDetailsList);

            orderRepository.save(existingOrder);

            return orderMapper.orderFromOrderEntity(existingOrder);
        } catch (IllegalArgumentException e) {

            throw e;
        } catch (Exception e) {

            throw new RuntimeException("Internal Server Error",e);
        }
    }

    @Override
    @Transactional
    public void deleteOrder(Integer id) {

        Optional<Order> optionalOrder = orderRepository.findById(id);

        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            orderRepository.delete(order);
        }else{
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
    }
}
