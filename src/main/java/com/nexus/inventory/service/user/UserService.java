package com.nexus.inventory.service.user;

import com.nexus.inventory.model.user.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User findById(Long userId);

    List<User> findAllUsers();

    void deleteUser(Long userId);
}
