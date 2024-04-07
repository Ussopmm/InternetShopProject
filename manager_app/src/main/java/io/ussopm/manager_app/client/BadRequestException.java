package io.ussopm.manager_app.client;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends RuntimeException{
    List<String> errors;

    public BadRequestException(List<String> errors) {
        this.errors = errors;
    }
}
