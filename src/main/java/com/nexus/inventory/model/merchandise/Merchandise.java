package com.nexus.inventory.model.merchandise;


import com.nexus.inventory.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "merchandise")
public class Merchandise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @ManyToOne
    @JoinColumn(name = "registered_by_id")
    private User registeredBy;

    @ManyToOne
    @JoinColumn(name = "edited_by_id")
    private User editedBy;

    @Column(name = "edit_date")
    private LocalDate editDate;
}
