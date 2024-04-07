package io.ussopm.manager_app.controller;

import io.ussopm.manager_app.client.BadRequestException;
import io.ussopm.manager_app.client.ProductRestClient;
import io.ussopm.manager_app.controller.payload.UpdateProductPayload;
import io.ussopm.manager_app.model.Product;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/shop/product/{id}")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRestClient restClient;

    @ModelAttribute("product")
    public Product product(@PathVariable("id") int id) {
        return this.restClient.productById(id).orElseThrow(() -> new NoSuchElementException("Product with this id not found"));
    }

    @GetMapping
    public String getProductById() {
        return "product";
    }

    @GetMapping("/edit")
    public String updateProductPage() {
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("product") Product product, UpdateProductPayload payload, Model model, HttpServletResponse response) {
        try {
            this.restClient.updateProductById(product.id(), payload.productName(), payload.description());
            return "redirect:/shop/product/" + product.id();
        } catch (BadRequestException ex) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAttribute("errors", ex.getErrors());
            model.addAttribute("payload", payload);
            return "edit";
        }
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("product") Product product) {
        this.restClient.deleteProductById(product.id());
        return "redirect:/shop/products";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleException(NoSuchElementException exception, Model model, HttpServletResponse response) {

        model.addAttribute("error", exception.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());

        return "errors/error404";

    }
}
