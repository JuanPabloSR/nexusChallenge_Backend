package com.nexus.inventory.dtos.merchandise;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MerchandiseDTOTest {
    @Test
    void testMerchandiseDTO() {
        // Crear una instancia de MerchandiseDTO utilizando el constructor por defecto
        MerchandiseDTO merchandise = new MerchandiseDTO();

        // Establecer el valor del campo editDate utilizando el método setEditDate
        LocalDate editDate = LocalDate.of(2023, 9, 5);
        merchandise.setEditDate(editDate);

        // Verificar que el valor del campo editDate sea correcto utilizando el método getEditDate
        assertEquals(editDate, merchandise.getEditDate());
    }
}