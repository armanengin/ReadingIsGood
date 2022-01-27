package com.arman.reading.sgood.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ErrorDetail {
    private Date timestamp;
    private String message;
    private String details;
}
