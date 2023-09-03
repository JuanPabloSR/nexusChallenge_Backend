package com.nexus.inventory.dtos.merchandise;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MerchandiseDTO {

    private String productName;
    private int quantity;
    private LocalDate entryDate;
    private Long registeredById;
    private Long editedById;
    private LocalDate editDate;

}
