package com.nexus.inventory.dtos.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder

public class ErrorDTO {
    private String message;
    private HttpStatus status;
}
