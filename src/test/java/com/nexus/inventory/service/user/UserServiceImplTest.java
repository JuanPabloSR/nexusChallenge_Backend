package com.nexus.inventory.service.user;

import com.nexus.inventory.dtos.user.UserDTO;
import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.position.Position;
import com.nexus.inventory.model.user.User;
import com.nexus.inventory.repository.user.UserRepository;
import com.nexus.inventory.service.position.PositionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;


@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PositionService positionService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO createUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Juan");
        userDTO.setAge(30);
        userDTO.setPositionId(1L);
        userDTO.setJoinDate(LocalDate.now());
        return userDTO;
    }

    private Position createPosition() {
        Position position = new Position();
        position.setIdPosition(1L);
        position.setJobTitle("Asesor comercial");
        return position;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Juan");
        user.setAge(30);
        return user;
    }


    @Test
    void testSaveUser() {
        // Crea un objeto UserDTO para enviar al método saveUser
        UserDTO userDTO = createUserDTO();

        // Crea un objeto Position para devolver en el método findById del servicio PositionService
        Position position = createPosition();

        // Define el comportamiento de los objetos simulados
        when(positionService.findById(1L)).thenReturn(Optional.of(position));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llama al método saveUser y verifica el resultado
        User savedUser = userService.saveUser(userDTO);
        assertEquals("Juan", savedUser.getName());
        assertEquals(30, savedUser.getAge());
        assertEquals("Asesor comercial", savedUser.getPosition().getJobTitle());
    }

    @Test
    void testSaveUserWhenPositionDoesNotExist() {
        // Crea un objeto UserDTO para enviar al método saveUser
        UserDTO userDTO = createUserDTO();

        // Define el comportamiento de los objetos simulados
        when(positionService.findById(1L)).thenReturn(Optional.empty());

        // Llama al método saveUser y verifica que se lance una excepción
        assertThrows(RequestException.class, () -> userService.saveUser(userDTO));
    }

    @Test
    void testSaveUserWhenUserAlreadyExists() {
        // Crea un objeto UserDTO para enviar al método saveUser
        UserDTO userDTO = createUserDTO();

        // Define el comportamiento de los objetos simulados
        when(userRepository.existsByName("Juan")).thenReturn(true);

        // Llama al método saveUser y verifica que se lance una excepción
        assertThrows(RequestException.class, () -> userService.saveUser(userDTO));
    }

    @Test
    void testGetValidJoinDateWhenJoinDateIsInvalid() {
        // Llama al método getValidJoinDate con una fecha inválida y verifica que se lance una excepción
        assertThrows(RequestException.class, () -> userService.getValidJoinDate(LocalDate.now().plusDays(1)));
    }

    @Test
    void testEditUser() {
        // Crea un objeto UserDTO para enviar al método editUser
        UserDTO userDTO = createUserDTO();

        // Crea un objeto User para devolver en el método findById del repositorio UserRepository
        User user = createUser();

        // Crea un objeto Position para devolver en el método findById del servicio PositionService
        Position position = createPosition();

        // Define el comportamiento de los objetos simulados
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(positionService.findById(1L)).thenReturn(Optional.of(position));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llama al método editUser y verifica el resultado
        User updatedUser = userService.editUser(1L, userDTO);
        assertEquals("Juan", updatedUser.getName());
        assertEquals(30, updatedUser.getAge());
        assertEquals("Asesor comercial", updatedUser.getPosition().getJobTitle());
    }

    @Test
    void testEditUserWhenUserDoesNotExist() {
        // Crea un objeto UserDTO para enviar al método editUser
        UserDTO userDTO = createUserDTO();

        // Define el comportamiento de los objetos simulados
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Llama al método editUser y verifica que se lance una excepción
        assertThrows(RequestException.class, () -> userService.editUser(1L, userDTO));
    }

    @Test
    void testFindById() {
        // Crea un objeto User para devolver en el método findById del repositorio UserRepository
        User user = createUser();

        // Define el comportamiento del objeto simulado
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Llama al método findById y verifica el resultado
        User foundUser = userService.findById(1L);
        assertEquals("Juan", foundUser.getName());
        assertEquals(30, foundUser.getAge());
    }

    @Test
    void testFindByIdNotFound() {
        // Define el comportamiento del objeto simulado
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Llama al método findById y verifica que se lance una excepción
        assertThrows(RequestException.class, () -> userService.findById(1L));
    }


    @Test
    void testFindAllUsers() {
        // Crea una lista de objetos User para devolver en el método findAll del repositorio UserRepository
        List<User> users = new ArrayList<>();
        User user1 = createUser();
        users.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Pedro");
        user2.setAge(40);
        users.add(user2);

        // Define el comportamiento del objeto simulado
        when(userRepository.findAll()).thenReturn(users);

        // Llama al método findAllUsers y verifica el resultado
        List<User> foundUsers = userService.findAllUsers();
        assertEquals(2, foundUsers.size());
        assertEquals("Juan", foundUsers.get(0).getName());
        assertEquals(30, foundUsers.get(0).getAge());
        assertEquals("Pedro", foundUsers.get(1).getName());
        assertEquals(40, foundUsers.get(1).getAge());
    }

    @Test
    void testFindAllUsersWhenNoUsersExist() {
        // Define el comportamiento del objeto simulado
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Llama al método findAllUsers y verifica que se lance una excepción
        assertThrows(RequestException.class, () -> userService.findAllUsers());
    }

    @Test
    void testDeleteUser() {
        // Define el comportamiento del objeto simulado
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        // Llama al método deleteUser y verifica que no se lance ninguna excepción
        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    @Test
    void testDeleteUserWhenUserDoesNotExist() {
        // Define el comportamiento del objeto simulado
        when(userRepository.existsById(1L)).thenReturn(false);

        // Llama al método deleteUser y verifica que se lance una excepción
        assertThrows(RequestException.class, () -> userService.deleteUser(1L));
    }


    @Test
    void testGetValidJoinDateWhenJoinDateIsNull() {
        // Llama al método getValidJoinDate con joinDate = null y verifica que se lance una excepción
        assertThrows(RequestException.class, () -> userService.getValidJoinDate(null));
    }

    @Test
    void testGetValidJoinDateWhenJoinDateIsInFuture() {
        // Llama al método getValidJoinDate con una fecha en el futuro y verifica que se lance una excepción
        assertThrows(RequestException.class, () -> userService.getValidJoinDate(LocalDate.now().plusDays(1)));
    }
}
