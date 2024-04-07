package io.ussopm.manager_app.controller;

import io.ussopm.manager_app.client.BadRequestException;
import io.ussopm.manager_app.client.ProductRestClient;
import io.ussopm.manager_app.controller.payload.NewProductPayload;
import io.ussopm.manager_app.model.Product;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shop/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductRestClient restClient;

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", this.restClient.findAllProducts());
        return "list";
    }

    @GetMapping("/new")
    public String creatingProductPage(@ModelAttribute("product") NewProductPayload payload) {
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("product") NewProductPayload payload, Model model, HttpServletResponse response) {
        try {
            this.restClient.createProduct(payload.productName(), payload.description());
            return "redirect:/shop/products";
        } catch (BadRequestException ex) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAttribute("payload", payload);
            model.addAttribute("errors", ex.getErrors());
            return "new";
        }
    }
}
