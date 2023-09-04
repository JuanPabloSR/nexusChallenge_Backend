package com.nexus.inventory.repository.merchandise;

import com.nexus.inventory.model.merchandise.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {
    boolean existsByProductName(String productName);

    List<Merchandise> findByEntryDate(LocalDate entryDate);

    @Query("SELECT m FROM Merchandise m WHERE LOWER(m.productName) LIKE LOWER(concat('%', :searchTerm, '%')) OR LOWER(m.registeredBy.name) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<Merchandise> findByProductName(@Param("searchTerm") String searchTerm);}
