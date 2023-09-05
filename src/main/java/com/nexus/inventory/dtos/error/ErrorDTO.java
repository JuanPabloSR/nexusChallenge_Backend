package com.nexus.inventory.dtos.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder

public class ErrorDTO {
    private String message;
    private HttpStatus status;
}
