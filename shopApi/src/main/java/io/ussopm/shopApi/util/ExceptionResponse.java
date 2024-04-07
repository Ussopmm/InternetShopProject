package io.ussopm.shopApi.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor

public class ExceptionResponse {

    private Integer errorType;

    private String message;

    private LocalDateTime timeStamp;
}
