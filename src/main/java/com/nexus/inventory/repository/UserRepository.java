package com.nexus.inventory.repository;

import com.nexus.inventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
