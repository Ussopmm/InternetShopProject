package io.ussopm.manager_app.client;

import io.ussopm.manager_app.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRestClient {

    List<Product> findAllProducts();

    Optional<Product> productById(Integer id);

    Product createProduct(String productName, String description);

    void updateProductById(Integer id, String productName, String description);

    void deleteProductById(Integer id);
}
