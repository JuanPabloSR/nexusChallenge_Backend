package com.nexus.inventory.repository.merchandise;

import com.nexus.inventory.model.merchandise.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {
    boolean existsByProductName(String productName);
}
