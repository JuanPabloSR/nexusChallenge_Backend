package com.nexus.inventory.service.user;

import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.user.User;
import com.nexus.inventory.repository.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)){
            throw new RequestException(HttpStatus.NOT_FOUND, "The User you wish to delete does not exist");
        }
        userRepository.deleteById(userId);
    }
}
