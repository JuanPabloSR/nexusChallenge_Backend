package com.nexus.inventory.service.position;

import com.nexus.inventory.model.position.Position;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    Position savePosition(Position position);

    void deletePosition(Long positionId);

    List<Position> getAllPositions();

    Optional<Position> findById(Long positionId);
}
