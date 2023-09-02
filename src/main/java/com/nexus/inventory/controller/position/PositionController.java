package com.nexus.inventory.controller.position;

import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.position.Position;
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
            throw new RequestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPositions() {
        try {
            return ResponseEntity.ok(positionService.findAllPositions());
        } catch (Exception e) {
            throw new RequestException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @DeleteMapping("{positionId}")
    public ResponseEntity<?> deletePosition(@PathVariable Long positionId) {
        try {
            positionService.deletePosition(positionId);
            return new ResponseEntity<>("Position successfully deleted",HttpStatus.OK);
        } catch (Exception e) {
            throw new RequestException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
