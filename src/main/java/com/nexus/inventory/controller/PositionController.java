package com.nexus.inventory.controller;

import com.nexus.inventory.model.Position;
import com.nexus.inventory.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<?> savePosition(@RequestBody Position position) {
        return new ResponseEntity<>(positionService.savePosition(position), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllPositions() {
        return ResponseEntity.ok(positionService.findAllPositions());
    }

    @DeleteMapping("{positionId}")
    public ResponseEntity<?> deletePosition(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
