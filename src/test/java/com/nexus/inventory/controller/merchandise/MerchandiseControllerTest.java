package com.nexus.inventory.controller.merchandise;

import com.nexus.inventory.dtos.merchandise.MerchandiseDTO;
import com.nexus.inventory.model.merchandise.Merchandise;
import com.nexus.inventory.service.merchandise.MerchandiseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.doNothing;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MerchandiseController.class)
class MerchandiseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MerchandiseService merchandiseService;

    // Función auxiliar para convertir objetos a JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetMerchandiseById() throws Exception {
        Long merchandiseId = 1L;

        Merchandise merchandise = new Merchandise();
        merchandise.setId(merchandiseId);
        merchandise.setProductName("Producto");
        merchandise.setQuantity(50);
        merchandise.setEntryDate(LocalDate.now());

        when(merchandiseService.findById(merchandiseId)).thenReturn(merchandise);

        mockMvc.perform(get("/api/merchandise/{merchandiseId}", merchandiseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(merchandiseId))
                .andExpect(jsonPath("$.productName").value("Producto"))
                .andExpect(jsonPath("$.quantity").value(50))
                .andExpect(jsonPath("$.entryDate").value(LocalDate.now().toString()));
    }

    @Test
    void testGetAllMerchandise() throws Exception {
        LocalDate entryDate = LocalDate.now();
        String searchTerm = "searchTerm";
        Pageable pageable = PageRequest.of(0, 10);

        List<Merchandise> merchandiseList = new ArrayList<>();
        Merchandise merchandise = new Merchandise();
        merchandise.setId(1L);
        merchandise.setProductName("Producto");
        merchandise.setQuantity(50);
        merchandise.setEntryDate(entryDate);
        merchandiseList.add(merchandise);

        Page<Merchandise> merchandisePage = new PageImpl<>(merchandiseList);

        when(merchandiseService.findAllMerchandise(entryDate, searchTerm, pageable)).thenReturn(merchandisePage);

        mockMvc.perform(get("/api/merchandise")
                        .param("entryDate", entryDate.toString())
                        .param("searchTerm", searchTerm)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteMerchandise() throws Exception {
        Long merchandiseId = 1L;
        Long userId = 1L;
        Map<String, Long> requestBody = new HashMap<>();
        requestBody.put("userId", userId);

        doNothing().when(merchandiseService).deleteMerchandise(merchandiseId, userId);

        mockMvc.perform(delete("/api/merchandise/{merchandiseId}", merchandiseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateMerchandise() throws Exception {
        String merchandiseDTOJson = "{" +
                "\"productName\": \"Producto\"," +
                "\"quantity\": 50," +
                "\"entryDate\": \"" + LocalDate.now().toString() + "\"" +
                "}";

        Merchandise savedMerchandise = new Merchandise();
        savedMerchandise.setId(1L);
        savedMerchandise.setProductName("Producto");
        savedMerchandise.setQuantity(50);
        savedMerchandise.setEntryDate(LocalDate.now());

        when(merchandiseService.saveMerchandise(any(MerchandiseDTO.class))).thenReturn(savedMerchandise);

        mockMvc.perform(post("/api/merchandise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(merchandiseDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedMerchandise.getId()))
                .andExpect(jsonPath("$.productName").value(savedMerchandise.getProductName()))
                .andExpect(jsonPath("$.quantity").value(savedMerchandise.getQuantity()))
                .andExpect(jsonPath("$.entryDate").value(savedMerchandise.getEntryDate().toString()));
    }

    @Test
    void testUpdateMerchandise() throws Exception {
        Long merchandiseId = 1L;

        String updateMerchandiseDTOJson = "{" +
                "\"productName\": \"Producto actualizado\"," +
                "\"quantity\": 75," +
                "\"entryDate\": \"" + LocalDate.now().toString() + "\"" +
                "}";

        Merchandise updatedMerchandise = new Merchandise();
        updatedMerchandise.setId(merchandiseId);
        updatedMerchandise.setProductName("Producto actualizado");
        updatedMerchandise.setQuantity(75);
        updatedMerchandise.setEntryDate(LocalDate.now());
        updatedMerchandise.setEditDate(LocalDate.now());

        when(merchandiseService.updateMerchandise(eq(merchandiseId), any(MerchandiseDTO.class))).thenReturn(updatedMerchandise);

        mockMvc.perform(put("/api/merchandise/{merchandiseId}", merchandiseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateMerchandiseDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedMerchandise.getId()))
                .andExpect(jsonPath("$.productName").value(updatedMerchandise.getProductName()))
                .andExpect(jsonPath("$.quantity").value(updatedMerchandise.getQuantity()))
                .andExpect(jsonPath("$.entryDate").value(updatedMerchandise.getEntryDate().toString()))
                .andExpect(jsonPath("$.editDate").value(updatedMerchandise.getEditDate().toString()));
    }

}

