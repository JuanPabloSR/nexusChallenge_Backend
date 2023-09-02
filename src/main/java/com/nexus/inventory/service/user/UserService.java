package com.nexus.inventory.service.user;

import com.nexus.inventory.dtos.user.UserDTO;
import com.nexus.inventory.model.user.User;

import java.util.List;

public interface UserService {

    User saveUser(UserDTO userDTO);

    User findById(Long userId);

    User editUser(Long userId, UserDTO updateUserDto);

    List<User> findAllUsers();

    void deleteUser(Long userId);
}
