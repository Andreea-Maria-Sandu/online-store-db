package com.example.finalproject.testing;

import com.example.finalproject.dto.response.product.ProductResponse;
import com.example.finalproject.entity.Product;
import com.example.finalproject.exception.order.OrderNotFoundException;
import com.example.finalproject.exception.product.ProductNotFoundException;
import com.example.finalproject.mapper.ProductMapper;
import com.example.finalproject.repository.ProductRepository;
import com.example.finalproject.service.ProductService;
import com.example.finalproject.service.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import javax.sound.sampled.Port;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class ProductTest {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductRepository productRepository;
    @Test
    public void getProductById(){
        Product product = new Product();
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        ProductResponse productResponse = new ProductResponse();
        when(productMapper.fromProductEntity(product)).thenReturn(productResponse);

        Assertions.assertEquals(productResponse,productService.getProductById(anyInt()));
    }
    @Test
    public void productNotFound(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class,()->productService.getProductById(anyInt()));
    }
}
