package com.nexus.inventory.repository.position;

import com.nexus.inventory.model.position.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
