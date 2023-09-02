package com.nexus.inventory.service.user;

import com.nexus.inventory.dtos.user.UserDTO;
import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.position.Position;
import com.nexus.inventory.model.user.User;
import com.nexus.inventory.repository.user.UserRepository;
import com.nexus.inventory.service.position.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PositionService positionService;

    public UserServiceImpl(UserRepository userRepository, PositionService positionService) {
        this.userRepository = userRepository;
        this.positionService = positionService;
    }


    @Override
    public User saveUser(UserDTO userDTO) {
        Optional<Position> optionalPosition = positionService.findById(userDTO.getPositionId());
        if (optionalPosition.isPresent()) {
            Position position = optionalPosition.get();

            User user = new User();
            user.setName(userDTO.getName());
            user.setAge(userDTO.getAge());
            user.setPosition(position);
            user.setJoinDate(userDTO.getJoinDate());
            return userRepository.save(user);

        } else {
            throw new RequestException(HttpStatus.BAD_REQUEST, "The position does not exist");
        }
    }


    @Override
    public User editUser(Long userId, UserDTO updateUserDto) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new RequestException(HttpStatus.NOT_FOUND, "The user you want to edit does not exist"));

        Optional<Position> optionalPosition = positionService.findById(updateUserDto.getPositionId());

        if (optionalPosition.isPresent()) {
            Position newPosition = optionalPosition.get();

            userToUpdate.setPosition(newPosition);
            userToUpdate.setName(updateUserDto.getName());
            userToUpdate.setAge(updateUserDto.getAge());
            userToUpdate.setJoinDate(updateUserDto.getJoinDate());

            return userRepository.save(userToUpdate);
        } else {
            throw new RequestException(HttpStatus.NOT_FOUND, "Position not found");
        }
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RequestException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RequestException(HttpStatus.NOT_FOUND, "The User you wish to delete does not exist");
        }
        userRepository.deleteById(userId);
    }
}
