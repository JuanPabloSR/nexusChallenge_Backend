package com.nexus.inventory.service.user;

import com.nexus.inventory.dtos.user.UserDTO;
import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.position.Position;
import com.nexus.inventory.model.user.User;
import com.nexus.inventory.repository.user.UserRepository;
import com.nexus.inventory.service.position.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        validateUserDoesNotExist(userDTO.getName());

        Position position = getPositionOrThrow(userDTO.getPositionId());
        LocalDate joinDate = getValidJoinDate(userDTO.getJoinDate());

        User user = createUserFromDTO(userDTO, position, joinDate);
        return userRepository.save(user);
    }

    private void validateUserDoesNotExist(String userName) {
        if (userRepository.existsByName(userName)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "The user already exists");
        }
    }

    private Position getPositionOrThrow(Long positionId) {
        Optional<Position> optionalPosition = positionService.findById(positionId);
        return optionalPosition.orElseThrow(() -> new RequestException(HttpStatus.BAD_REQUEST, "The position does not exist"));
    }

    private LocalDate getValidJoinDate(LocalDate joinDate) {
        LocalDate currentDate = LocalDate.now();
        if (joinDate == null || joinDate.isAfter(currentDate)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Invalid joinDate");
        }
        return joinDate;
    }

    private User createUserFromDTO(UserDTO userDTO, Position position, LocalDate joinDate) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setPosition(position);
        user.setJoinDate(joinDate);
        return user;
    }


    @Override
    public User editUser(Long userId, UserDTO updateUserDto) {
        User userToUpdate = getUserOrThrow(userId);
        Position newPosition = getPositionOrThrow(updateUserDto.getPositionId());
        LocalDate joinDate = getValidJoinDate(updateUserDto.getJoinDate());

        updateUserFromDTO(userToUpdate, updateUserDto, newPosition, joinDate);
        return userRepository.save(userToUpdate);
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RequestException(HttpStatus.NOT_FOUND, "The user you want to edit does not exist"));
    }

    private void updateUserFromDTO(User userToUpdate, UserDTO updateUserDto, Position newPosition, LocalDate joinDate) {
        userToUpdate.setPosition(newPosition);
        userToUpdate.setName(updateUserDto.getName());
        userToUpdate.setAge(updateUserDto.getAge());
        userToUpdate.setJoinDate(joinDate);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RequestException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RequestException(HttpStatus.NOT_FOUND, "No users exist");
        }
        return users;
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RequestException(HttpStatus.NOT_FOUND, "The User you wish to delete does not exist");
        }
        userRepository.deleteById(userId);
    }
}
