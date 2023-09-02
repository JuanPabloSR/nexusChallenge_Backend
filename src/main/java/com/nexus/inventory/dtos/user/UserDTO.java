package com.nexus.inventory.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDTO {
    private String name;
    private int age;
    private Long positionId;
    private LocalDate joinDate;
}
