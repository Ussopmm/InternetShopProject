package io.ussopm.shopApi.controller;


import io.ussopm.shopApi.controller.payload.UpdateProductPayload;
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

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/shop/api/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductServiceImpl productService;
    private final BadRequestExceptionHandler handler;

    @GetMapping("/{id}")
    public Product productById(@PathVariable("id") int id) {
        return productService.getProductById(id)
                .orElseThrow(() ->new NoSuchElementException("Product not found"));
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable("id") int id, @RequestBody @Valid UpdateProductPayload payload, BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            productService.updateProductById(id, payload.productName(), payload.description());
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProductById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        ex.getMessage()));
    }

}
