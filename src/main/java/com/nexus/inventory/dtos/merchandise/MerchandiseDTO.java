package com.nexus.inventory.dtos.merchandise;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MerchandiseDTO {

    private String productName;
    private int quantity;
    private LocalDate entryDate;
    private Long registeredById;
    private Long editedById;
    private LocalDate editDate;

}
