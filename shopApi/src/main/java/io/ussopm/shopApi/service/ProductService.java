package io.ussopm.shopApi.service;

import io.ussopm.shopApi.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(int id);

    List<Product> getAllProducts();

    void createNewProduct(String productName, String description);

    void updateProductById(int id, String s, String description);

    void deleteProductById(int id);
}
