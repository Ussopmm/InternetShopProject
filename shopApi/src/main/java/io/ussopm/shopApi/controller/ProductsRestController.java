package io.ussopm.shopApi.controller;


import io.ussopm.shopApi.controller.payload.NewProductPayload;
import io.ussopm.shopApi.errors.ProductNotCreatedException;
import io.ussopm.shopApi.model.Product;
import io.ussopm.shopApi.service.impl.ProductServiceImpl;
import io.ussopm.shopApi.util.BadRequestExceptionHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/api/products")
@RequiredArgsConstructor
public class ProductsRestController {

    private final ProductServiceImpl productService;
    private final BadRequestExceptionHandler handler;

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createNewProduct(@Valid @RequestBody NewProductPayload payload, BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.productService.createNewProduct(payload.productName(), payload.description());
            return new ResponseEntity<>(HttpStatus.CREATED); // 201 HTTP
        }
    }


    @ExceptionHandler(ProductNotCreatedException.class)
    public ResponseEntity<ProblemDetail> handleProductNotCreatedException(ProductNotCreatedException ex) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                 .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                         ex.getMessage()));// HTTP 400
    }

}
