package com.nexus.inventory.repository.user;

import com.nexus.inventory.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
}
