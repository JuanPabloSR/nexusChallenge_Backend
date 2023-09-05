package com.nexus.inventory.dtos.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class ErrorDTOTest {

    @Test
    void testErrorDTO() {
        // Crear una instancia de ErrorDTO utilizando el constructor @Builder
        ErrorDTO error = ErrorDTO.builder()
                .message("Error message")
                .status(HttpStatus.BAD_REQUEST)
                .build();

        // Verificar que los valores de los campos sean correctos
        assertEquals("Error message", error.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());

        // Cambiar el valor de un campo y verificar que el cambio se haya realizado correctamente
        error.setMessage("New error message");
        assertEquals("New error message", error.getMessage());

        // Verificar el método toString()
        String actualToString = error.toString();
        assertEquals(actualToString, actualToString);
    }
}