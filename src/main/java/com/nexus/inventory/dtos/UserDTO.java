package com.nexus.inventory.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private String name;
    private int age;
    private Long positionId;
    private LocalDate joinDate;
}
