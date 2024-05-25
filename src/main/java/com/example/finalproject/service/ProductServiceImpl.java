package com.example.finalproject.service;

import com.example.finalproject.dto.request.product.AddProductRequest;
import com.example.finalproject.dto.request.product.UpdateProductRequest;
import com.example.finalproject.dto.response.product.ProductResponse;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.exception.category.CategoryNotFoundException;
import com.example.finalproject.exception.product.ProductNotFoundException;
import com.example.finalproject.mapper.ProductMapper;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private final  ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = products.stream().map(productMapper::fromProductEntity).collect(Collectors.toList());

        return productResponses;
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            ProductResponse productResponse = productMapper.fromProductEntity(product);
            return productResponse;
        }else{
            throw new ProductNotFoundException("Product not found");//replace with productNotFoundException
        }
    }

    @Override
    public ProductResponse addProduct(AddProductRequest addProductRequest) {
        Product product = productMapper.fromRequestProduct(addProductRequest);

        // verifica dacă produsul exista deja în baza de date în functie de categorie si nume
        Optional<Product> optionalProduct = productRepository.findByCategoryNameAndProductName(addProductRequest.getCategoryName(), product.getProductName());

        if (optionalProduct.isPresent()) {
            //produsul exista deja actualizeaza doar cantitatea si pretul
            Product existingProduct = optionalProduct.get();
            int newQuantity = existingProduct.getProductQuantity() + product.getProductQuantity();
            double newPrice = addProductRequest.getPrice(); // seteaza noul pret
            existingProduct.setProductQuantity(newQuantity);
            existingProduct.setProductPrice(newPrice);
            productRepository.save(existingProduct);

            // ia categoria din baza de date pentru a te asigura ca este actualizata si nu este null
            Optional<Category> optionalCategory = categoryRepository.findById(existingProduct.getCategory().getId());
            String categoryName = optionalCategory.map(Category::getName).orElse(null);

            // returneaza raspunsul cu categoria actualizata
            return new ProductResponse(existingProduct.getProductName(), existingProduct.getProductPrice(), existingProduct.getProductQuantity(), categoryName);
        } else {
            // produsul nu exista adauga unul nou
            Optional<Category> optionalCategory = categoryRepository.findByName(addProductRequest.getCategoryName());
            if (optionalCategory.isPresent()) {
                product.setCategory(optionalCategory.get());
                productRepository.save(product);

            } else {
                throw new RuntimeException("Category does not exist!");
            }
        }

        ProductResponse productResponse = productMapper.fromProductEntity(product);
        return productResponse;
    }
    @Override
    public void updateProduct(Integer id, UpdateProductRequest updateProductRequest) {

        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();
            Product productToBeUpdated = productMapper.updateProductRequest(product,updateProductRequest);
            productRepository.save(productToBeUpdated);
        }else {
            throw new ProductNotFoundException("Product not found");
        }

    }

    @Override
    public void deleteProductById(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()){
            productRepository.deleteById(id);
        }else {
            throw new ProductNotFoundException("Product not found");
        }
    }
}
