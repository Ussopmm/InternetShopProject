package io.ussopm.shopApi.errors;

public class ProductNotCreatedException extends RuntimeException{
    public ProductNotCreatedException(String msg) {
        super(msg);
    }
}
