package com.nexus.inventory.service.position;

import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.position.Position;
import com.nexus.inventory.repository.position.PositionRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionServiceImpl positionService;

    @Test
    void testSavePosition() {
        Position position = new Position();
        position.setJobTitle("Asesor comercial");

        when(positionRepository.save(position)).thenReturn(position);
        Position savedPosition = positionService.savePosition(position);

        verify(positionRepository, times(1)).save(position);
        assertEquals(position, savedPosition);
    }

    @Test
    void testDeletePosition() {
        Long positionId = 1L;
        when(positionRepository.existsById(positionId)).thenReturn(true);

        positionService.deletePosition(positionId);

        verify(positionRepository, times(1)).existsById(positionId);
        verify(positionRepository, times(1)).deleteById(positionId);
    }

    @Test
    void testDeleteNonExistingPosition() {
        Long positionId = 1L;

        when(positionRepository.existsById(positionId)).thenReturn(false);

        assertThrows(RequestException.class, () -> {
            positionService.deletePosition(positionId);
        });
    }

    @Test
    void testFindById() {
        Long positionId = 1L;
        Position position = new Position();
        position.setIdPosition(positionId);
        position.setJobTitle("Asesor comercial");

        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));
        Optional<Position> result = positionService.findById(positionId);

        verify(positionRepository, times(1)).findById(positionId);
        assertTrue(result.isPresent());
        assertEquals(position, result.get());
    }

    @Test
    void testGetAllPositions() {
        List<Position> positions = new ArrayList<>();
        Position position1 = new Position();
        position1.setIdPosition(1L);
        position1.setJobTitle("Asesor comercial");
        positions.add(position1);
        Position position2 = new Position();
        position2.setIdPosition(2L);
        position2.setJobTitle("Gerente de ventas");
        positions.add(position2);

        when(positionRepository.findAll()).thenReturn(positions);
        List<Position> result = positionService.getAllPositions();

        verify(positionRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(positions, result);
    }

    @Test
    void testGetAllPositionsWhenNoPositionsExist() {
        when(positionRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(RequestException.class, () -> {
            positionService.getAllPositions();
        });
    }


}