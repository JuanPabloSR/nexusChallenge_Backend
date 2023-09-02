package com.nexus.inventory.controller.user;

import com.nexus.inventory.dtos.user.UserDTO;
import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.position.Position;
import com.nexus.inventory.model.user.User;
import com.nexus.inventory.service.position.PositionService;
import com.nexus.inventory.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
            Optional<Position> optionalPosition = positionService.findById(userDTO.getPositionId());

            if (optionalPosition.isPresent()) {
                Position position = optionalPosition.get();

                User user = new User();
                user.setName(userDTO.getName());
                user.setAge(userDTO.getAge());
                user.setPosition(position);
                user.setJoinDate(userDTO.getJoinDate());

                User savedUser = userService.saveUser(user);
                return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
            } else {
                throw new RequestException(HttpStatus.BAD_REQUEST, "The position does not exist");
            }

    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        User user = userService.findById(userId);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new RequestException(HttpStatus.NOT_FOUND, "User not found");
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            throw new RequestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            throw new RequestException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
