package com.nexus.inventory.dtos.user;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String name;
    private int age;
    private Long positionId;
    private LocalDate joinDate;
}
