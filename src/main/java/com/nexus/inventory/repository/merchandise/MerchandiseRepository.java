package com.nexus.inventory.repository.merchandise;

import com.nexus.inventory.model.merchandise.Merchandise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {
    boolean existsByProductName(String productName);

    Page<Merchandise> findByEntryDate(LocalDate entryDate, Pageable pageable);

    @Query("SELECT m FROM Merchandise m WHERE LOWER(m.productName) LIKE LOWER(concat('%', :searchTerm, '%')) OR LOWER(m.registeredBy.name) LIKE LOWER(concat('%', :searchTerm, '%'))")
    Page<Merchandise> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT m FROM Merchandise m WHERE m.entryDate = :entryDate AND (LOWER(m.productName) LIKE LOWER(concat('%', :searchTerm, '%')) OR LOWER(m.registeredBy.name) LIKE LOWER(concat('%', :searchTerm, '%')))")
    Page<Merchandise> findByEntryDateAndSearchTerm(@Param("entryDate") LocalDate entryDate, @Param("searchTerm") String searchTerm, Pageable pageable);
}