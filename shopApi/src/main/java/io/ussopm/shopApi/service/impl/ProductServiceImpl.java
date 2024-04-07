package io.ussopm.shopApi.service.impl;

import io.ussopm.shopApi.errors.ProductNotFoundException;
import io.ussopm.shopApi.model.Product;
import io.ussopm.shopApi.repository.ProductRepository;
import io.ussopm.shopApi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    @Override
    public Optional<Product> getProductById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void createNewProduct(String productName, String description) {
        repository.save(new Product(null,productName, description));
    }

    @Override
    @Transactional
    public void updateProductById(int id, String productName, String description) {
        this.repository.findById(id).ifPresentOrElse(product ->  {
            product.setProductName(productName);
            product.setDescription(description);
        }, () -> {
            throw new NoSuchElementException();
        });
    }

    @Override
    public void deleteProductById(int id) {
        if (!repository.existsById(id)) {
           throw new NoSuchElementException();
        }
        this.repository.deleteById(id);
    }
}
