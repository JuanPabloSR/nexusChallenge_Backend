package com.nexus.inventory.service;

import com.nexus.inventory.model.Position;
import com.nexus.inventory.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository){
        this.positionRepository = positionRepository;
    }

    @Override
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public void deletePosition(Long positionId) {
        positionRepository.deleteById(positionId);
    }

    @Override
    public List<Position> findAllPositions() {
        return positionRepository.findAll();
    }
}
