package com.nexus.inventory.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RequestException extends RuntimeException {
    private HttpStatus status;

    public RequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
