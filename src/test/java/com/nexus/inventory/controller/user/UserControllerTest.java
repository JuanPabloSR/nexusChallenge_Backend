package com.nexus.inventory.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.inventory.dtos.user.UserDTO;
import com.nexus.inventory.model.position.Position;
import com.nexus.inventory.model.user.User;
import com.nexus.inventory.repository.user.UserRepository;
import com.nexus.inventory.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testGetUserById() throws Exception {
        // Crea un objeto User para devolver en el método findById del servicio UserService
        User user = new User();
        user.setId(1L);
        user.setName("Juan");
        user.setAge(30);
        user.setJoinDate(LocalDate.now());

        Position position = new Position();
        position.setIdPosition(1L);
        position.setJobTitle("Asesor comercial");
        user.setPosition(position);

        // Define el comportamiento del objeto simulado
        when(userService.findById(1L)).thenReturn(user);

        // Realiza una solicitud GET y verifica la respuesta
        mockMvc.perform(get("/api/user/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Juan")))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(jsonPath("$.joinDate", is(user.getJoinDate().toString())))
                .andExpect(jsonPath("$.position.idPosition", is(1)))
                .andExpect(jsonPath("$.position.jobTitle", is("Asesor comercial")));
    }

    @Test
    void getAllUsersTest() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Doe");
        List<User> users = Arrays.asList(user1, user2);

        given(userService.findAllUsers()).willReturn(users);

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(user1.getId()))
                .andExpect(jsonPath("$[0].name").value(user1.getName()))
                .andExpect(jsonPath("$[1].id").value(user2.getId()))
                .andExpect(jsonPath("$[1].name").value(user2.getName()));
    }

    @Test
    void deleteUserByIdTest() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(any(Long.class));

        mockMvc.perform(delete("/api/user/{userId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void editUserTest() throws Exception {
        UserDTO updateUserDTO = new UserDTO();
        updateUserDTO.setName("Jane Doe");
        updateUserDTO.setAge(30);

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName(updateUserDTO.getName());
        updatedUser.setAge(updateUserDTO.getAge());

        Mockito.when(userService.editUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/user/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateUserDTO)))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createUserTest() throws Exception {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setName("John Doe");
        newUserDTO.setAge(30);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(newUserDTO.getName());
        savedUser.setAge(newUserDTO.getAge());

        Mockito.when(userService.saveUser(any(UserDTO.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.name").value(savedUser.getName()))
                .andExpect(jsonPath("$.age").value(savedUser.getAge()));
    }


}