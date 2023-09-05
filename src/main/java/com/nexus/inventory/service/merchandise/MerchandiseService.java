package com.nexus.inventory.service.merchandise;

import com.nexus.inventory.dtos.merchandise.MerchandiseDTO;
import com.nexus.inventory.model.merchandise.Merchandise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface MerchandiseService {

    Merchandise saveMerchandise(MerchandiseDTO merchandiseDTO);

    Merchandise findById(Long merchandiseId);

    Merchandise updateMerchandise(Long merchandiseId, MerchandiseDTO updateMerchandiseDto);

    Page<Merchandise> findAllMerchandise(LocalDate entryDate, String productName, Pageable pageable);

    Page<Merchandise> findAllMerchandiseByEntryDate(LocalDate entryDate, Pageable pageable);

    Page<Merchandise> findAllMerchandiseBySearchTerm(String searchTerm, Pageable pageable);

    void deleteMerchandise(Long id, Long merchandiseId);

}
