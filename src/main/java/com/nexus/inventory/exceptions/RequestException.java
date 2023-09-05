package com.nexus.inventory.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class RequestException extends RuntimeException {
    private HttpStatus status;

    public RequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
