package com.nexus.inventory.service.position;

import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.position.Position;
import com.nexus.inventory.repository.position.PositionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public void deletePosition(Long positionId) {
        if (!positionRepository.existsById(positionId)) {
            throw new RequestException(HttpStatus.NOT_FOUND, "The position you wish to delete does not exist");
        }
        positionRepository.deleteById(positionId);
    }

    @Override
    public List<Position> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        if (positions.isEmpty()) {
            throw new RequestException(HttpStatus.NOT_FOUND, "No positions exist");
        }
        return positions;
    }

    @Override
    public Optional<Position> findById(Long positionId) {
        return positionRepository.findById(positionId);
    }
}
