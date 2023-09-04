package com.nexus.inventory.controller.merchandise;

import com.nexus.inventory.dtos.merchandise.MerchandiseDTO;
import com.nexus.inventory.model.merchandise.Merchandise;
import com.nexus.inventory.service.merchandise.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/merchandise")
public class MerchandiseController {

    @Autowired
    private MerchandiseService merchandiseService;

    @PostMapping
    public ResponseEntity<Merchandise> createMerchandise(@RequestBody MerchandiseDTO merchandiseDTO) {
        Merchandise savedMerchandise = merchandiseService.saveMerchandise(merchandiseDTO);
        return new ResponseEntity<>(savedMerchandise, HttpStatus.CREATED);
    }

    @PutMapping("/{merchandiseId}")
    public ResponseEntity<Merchandise> updateMerchandise(@PathVariable Long merchandiseId, @RequestBody MerchandiseDTO updateMerchandiseDTO) {
        Merchandise updatedMerchandise = merchandiseService.updateMerchandise(merchandiseId, updateMerchandiseDTO);
        return new ResponseEntity<>(updatedMerchandise, HttpStatus.OK);
    }

    @GetMapping("/{merchandiseId}")
    public ResponseEntity<Merchandise> getMerchandiseById(@PathVariable Long merchandiseId) {
        Merchandise merchandise = merchandiseService.findById(merchandiseId);
        return new ResponseEntity<>(merchandise, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Merchandise>> getAllMerchandise() {
        List<Merchandise> merchandiseList = merchandiseService.findAllMerchandise();
        return new ResponseEntity<>(merchandiseList, HttpStatus.OK);
    }

    @DeleteMapping("/{merchandiseId}")
    public ResponseEntity<String> deleteMerchandise(@PathVariable Long merchandiseId) {
        merchandiseService.deleteMerchandise(merchandiseId);
        return new ResponseEntity<>("Merchandise successfully deleted", HttpStatus.OK);
    }

}
