package com.nexus.inventory.service.merchandise;

import com.nexus.inventory.dtos.merchandise.MerchandiseDTO;
import com.nexus.inventory.model.merchandise.Merchandise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MerchandiseService {

    Merchandise saveMerchandise(MerchandiseDTO merchandiseDTO);

    Merchandise findById(Long merchandiseId);

    Merchandise updateMerchandise(Long merchandiseId, MerchandiseDTO updateMerchandiseDto);

    List<Merchandise> findAllMerchandise(LocalDate entryDate, String productName);

    List<Merchandise> findAllMerchandiseByEntryDate(LocalDate entryDate);

    List<Merchandise> findAllMerchandiseByProductName(String searchTerm);

    void deleteMerchandise(Long id, Long merchandiseId);


}
