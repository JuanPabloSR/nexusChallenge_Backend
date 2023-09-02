package com.nexus.inventory.controller;

import com.nexus.inventory.exceptions.PositionNotFoundException;
import com.nexus.inventory.model.Position;
import com.nexus.inventory.service.position.PositionService;
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
        try {
            return new ResponseEntity<>(positionService.savePosition(position), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPositions() {
        try {
            return ResponseEntity.ok(positionService.findAllPositions());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("{positionId}")
    public ResponseEntity<?> deletePosition(@PathVariable Long positionId) {
        try {
            positionService.deletePosition(positionId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PositionNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
