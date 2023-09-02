package com.nexus.inventory.controller.position;

import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.position.Position;
import com.nexus.inventory.service.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<?> savePosition(@RequestBody Position position) {
        Position savedPosition = positionService.savePosition(position);
        return new ResponseEntity<>(savedPosition, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllPositions() {
        List<Position> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    @DeleteMapping("{positionId}")
    public ResponseEntity<?> deletePosition(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return new ResponseEntity<>("Position successfully deleted", HttpStatus.OK);
    }

}
