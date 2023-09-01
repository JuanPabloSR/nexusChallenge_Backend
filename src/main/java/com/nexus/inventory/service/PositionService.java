package com.nexus.inventory.service;

import com.nexus.inventory.model.Position;

import java.util.List;

public interface PositionService {
    Position savePosition(Position position);

    void deletePosition(Long positionId);

    List<Position> findAllPositions();
}
