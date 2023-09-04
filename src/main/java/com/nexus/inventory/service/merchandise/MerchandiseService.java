package com.nexus.inventory.service.merchandise;

import com.nexus.inventory.dtos.merchandise.MerchandiseDTO;
import com.nexus.inventory.model.merchandise.Merchandise;

import java.time.LocalDate;
import java.util.List;

public interface MerchandiseService {

    Merchandise saveMerchandise(MerchandiseDTO merchandiseDTO);

    Merchandise findById(Long merchandiseId);

    Merchandise updateMerchandise(Long merchandiseId, MerchandiseDTO updateMerchandiseDto);

    List<Merchandise> findAllMerchandise();

    void deleteMerchandise(Long id, Long merchandiseId);

    List<Merchandise> findAllMerchandiseByEntryDate(LocalDate entryDate);
}
