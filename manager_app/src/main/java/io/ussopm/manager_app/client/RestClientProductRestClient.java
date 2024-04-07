package io.ussopm.manager_app.client;

import io.ussopm.manager_app.controller.payload.NewProductPayload;
import io.ussopm.manager_app.controller.payload.UpdateProductPayload;
import io.ussopm.manager_app.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RequiredArgsConstructor
public class RestClientProductRestClient implements ProductRestClient{

    private final RestClient restClient;
    private final static ParameterizedTypeReference<List<Product>> PRODUCT_TYPE_REFERENCE =
            new ParameterizedTypeReference<List<Product>>() {};
    @Override
    public List<Product> findAllProducts() {
        return this.restClient
                .get()
                .uri("/shop/api/products")
                .retrieve()
                .body(PRODUCT_TYPE_REFERENCE);
    }

    @Override
    public Optional<Product> productById(Integer id) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/shop/api/product/{id}", id)
                    .retrieve()
                    .body(Product.class));
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        }
    }

    @Override
    public Product createProduct(String productName, String description) {
        try {
            return this.restClient.post()
                    .uri("/shop/api/products/new")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductPayload(productName, description))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest ex) {
            ProblemDetail problemDetail = ex.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void updateProductById(Integer id, String productName, String description) {
        try {
            this.restClient
                    .patch()
                    .uri("/shop/api/product/{id}/edit", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateProductPayload(productName, description))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest ex) {
            ProblemDetail problemDetail = ex.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteProductById(Integer id) {
        try {
            this.restClient
                    .delete()
                    .uri("/shop/api/product/{id}/delete", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new NoSuchElementException(ex);
        }
    }
}
