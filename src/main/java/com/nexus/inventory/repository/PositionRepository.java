package com.nexus.inventory.repository;

import com.nexus.inventory.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
