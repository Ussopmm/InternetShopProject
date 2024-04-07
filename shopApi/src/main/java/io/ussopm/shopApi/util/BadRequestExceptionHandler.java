package io.ussopm.shopApi.util;

import io.ussopm.shopApi.errors.ProductNotCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class BadRequestExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleException(BindException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Error 404"
        );
        problemDetail.setProperty("errors", exception.getAllErrors()
                .stream().map(ObjectError::getDefaultMessage).toList());

        return ResponseEntity.badRequest()
                .body(problemDetail);
    }
}
