package com.nexus.inventory.service.position;

import com.nexus.inventory.exceptions.PositionNotFoundException;
import com.nexus.inventory.model.Position;
import com.nexus.inventory.repository.PositionRepository;
import com.nexus.inventory.service.position.PositionService;
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
        if(!positionRepository.existsById(positionId)){
            throw new PositionNotFoundException("The position you wish to delete does not exist");
        }
        positionRepository.deleteById(positionId);
    }

    @Override
    public List<Position> findAllPositions() {
        return positionRepository.findAll();
    }
}
