package io.ussopm.shopApi.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductPayload (@NotNull(message = "Product name have to be not null")
                                    @Size(min = 3, max = 100, message = "Product name size min = 3, max = 100")
                                    String productName,
                                    @Size( max = 500, message = "Definition has limitation of word 500")
                                    String description){
}
