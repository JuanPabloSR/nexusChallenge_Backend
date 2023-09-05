package com.nexus.inventory.controller.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.inventory.model.position.Position;
import com.nexus.inventory.service.position.PositionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PositionController.class)
@AutoConfigureMockMvc

class PositionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionService positionService;

    @Test
    void testGetAllPositions() throws Exception {
        List<Position> positions = new ArrayList<>();
        Position position1 = new Position();
        position1.setIdPosition(1L);
        position1.setJobTitle("Asesor comercial");
        positions.add(position1);
        Position position2 = new Position();
        position2.setIdPosition(2L);
        position2.setJobTitle("Gerente de ventas");
        positions.add(position2);

        when(positionService.getAllPositions()).thenReturn(positions);

        mockMvc.perform(get("/api/position"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idPosition", is(1)))
                .andExpect(jsonPath("$[0].jobTitle", is("Asesor comercial")))
                .andExpect(jsonPath("$[1].idPosition", is(2)))
                .andExpect(jsonPath("$[1].jobTitle", is("Gerente de ventas")));
    }

    @Test
    void testSavePosition() throws Exception {
        Position position = new Position();
        position.setJobTitle("Asesor comercial");

        when(positionService.savePosition(any(Position.class))).thenReturn(position);

        mockMvc.perform(post("/api/position")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(position)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jobTitle", is("Asesor comercial")));
    }

    @Test
    void testDeletePosition() throws Exception {
        doNothing().when(positionService).deletePosition(1L);

        mockMvc.perform(delete("/api/position/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Position successfully deleted"));
    }


}