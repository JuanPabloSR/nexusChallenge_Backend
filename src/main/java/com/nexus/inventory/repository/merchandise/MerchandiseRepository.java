package com.nexus.inventory.repository.merchandise;

import com.nexus.inventory.model.merchandise.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {
    boolean existsByProductName(String productName);

    List<Merchandise> findByEntryDate(LocalDate entryDate);
}
