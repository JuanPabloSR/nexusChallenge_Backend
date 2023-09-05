package com.nexus.inventory.service.merchandise;

import com.nexus.inventory.dtos.merchandise.MerchandiseDTO;
import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.merchandise.Merchandise;
import com.nexus.inventory.model.user.User;
import com.nexus.inventory.repository.merchandise.MerchandiseRepository;
import com.nexus.inventory.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MerchandiseServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private MerchandiseRepository merchandiseRepository;

    @InjectMocks
    private MerchandiseServiceImpl merchandiseService;

    @Mock
    private MerchandiseServiceImpl merchandiseService2;



    @Test
    void testSaveMerchandise() {

        User user = new User();
        user.setId(1L);

        // Configura el merchandiseDTO con el ID de usuario válido
        MerchandiseDTO merchandiseDTO = new MerchandiseDTO();
        merchandiseDTO.setProductName("Producto de prueba");
        merchandiseDTO.setQuantity(100);
        merchandiseDTO.setEntryDate(LocalDate.now());
        merchandiseDTO.setRegisteredById(user.getId());

        // Define el comportamiento de los mocks
        when(merchandiseRepository.existsByProductName(anyString())).thenReturn(false);
        when(userService.findById(anyLong())).thenReturn(user);
        when(merchandiseRepository.save(any(Merchandise.class))).thenAnswer(invocation -> {
            Merchandise merchandise = invocation.getArgument(0);
            merchandise.setId(1L); // Simula la asignación de ID por parte de la base de datos
            return merchandise;
        });

        // Llama al método que se va a probar
        Merchandise savedMerchandise = merchandiseService.saveMerchandise(merchandiseDTO);

        // Verifica que se haya llamado a los métodos adecuados
        verify(merchandiseRepository).existsByProductName(merchandiseDTO.getProductName());
        verify(userService).findById(merchandiseDTO.getRegisteredById());
        verify(merchandiseRepository).save(any(Merchandise.class));

        // Verifica que el resultado sea el esperado
        assertNotNull(savedMerchandise);
        assertEquals("Producto de prueba", savedMerchandise.getProductName());
        assertEquals(100, savedMerchandise.getQuantity());
        assertEquals(LocalDate.now(), savedMerchandise.getEntryDate());
    }

    @Test
    void testFindById() {
        Long merchandiseId = 1L;
        Merchandise expectedMerchandise = new Merchandise();
        expectedMerchandise.setId(merchandiseId);
        expectedMerchandise.setProductName("Product Name");
        expectedMerchandise.setQuantity(10);
        expectedMerchandise.setEntryDate(LocalDate.now());

        when(merchandiseRepository.findById(merchandiseId)).thenReturn(java.util.Optional.of(expectedMerchandise));

        Merchandise actualMerchandise = merchandiseService.findById(merchandiseId);
        assertEquals(expectedMerchandise, actualMerchandise);
    }

    @Test
    void testFindByIdThrowsExceptionWhenMerchandiseNotFound() {
        Long merchandiseId = 1L;

        when(merchandiseRepository.findById(merchandiseId)).thenReturn(Optional.empty());

        assertThrows(RequestException.class, () -> merchandiseService.findById(merchandiseId));
    }


    @Test
    void validateMerchandiseDoesNotExistTest() {
        MerchandiseDTO merchandiseDTO = new MerchandiseDTO();
        merchandiseDTO.setProductName("Producto existente");
        merchandiseDTO.setQuantity(10);
        merchandiseDTO.setRegisteredById(1L);
        merchandiseDTO.setEntryDate(LocalDate.now());

        Mockito.when(merchandiseRepository.existsByProductName("Producto existente")).thenReturn(true);

        Exception exception = assertThrows(RequestException.class, () -> {
            merchandiseService.saveMerchandise(merchandiseDTO);
        });

        String expectedMessage = "The merchandise Producto existente already exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetUserOrThrow() {
        // Configura el comportamiento del mock UserService
        Long validUserId = 1L;
        User expectedUser = new User();
        expectedUser.setId(validUserId);
        when(userService.findById(validUserId)).thenReturn(expectedUser);

        Long invalidUserId = 2L;
        when(userService.findById(invalidUserId)).thenReturn(null);

        // Verifica que el método devuelve el usuario esperado cuando se proporciona un ID de usuario válido
        User actualUser = merchandiseService.getUserOrThrow(validUserId);
        assertEquals(expectedUser, actualUser);

        // Verifica que el método lanza una excepción cuando se proporciona un ID de usuario no válido
        RequestException exception = assertThrows(RequestException.class, () -> merchandiseService.getUserOrThrow(invalidUserId));
        assertEquals("The user with ID " + invalidUserId + " does not exist", exception.getMessage());
    }

    @Test
    void testGetValidEntryDate() {
        // Configura una fecha de entrada válida y verifica que el método devuelve la fecha de entrada esperada
        LocalDate validEntryDate = LocalDate.now().minusDays(1);
        LocalDate actualEntryDate = merchandiseService.getValidEntryDate(validEntryDate);
        assertEquals(validEntryDate, actualEntryDate);

        // Configura una fecha de entrada no válida (nula) y verifica que el método lanza una excepción
        LocalDate nullEntryDate = null;
        RequestException exception1 = assertThrows(RequestException.class, () -> merchandiseService.getValidEntryDate(nullEntryDate));
        assertEquals("Invalid entryDate", exception1.getMessage());

        // Configura una fecha de entrada no válida (en el futuro) y verifica que el método lanza una excepción
        LocalDate futureEntryDate = LocalDate.now().plusDays(1);
        RequestException exception2 = assertThrows(RequestException.class, () -> merchandiseService.getValidEntryDate(futureEntryDate));
        assertEquals("Invalid entryDate", exception2.getMessage());
    }

    @Test
    void testDeleteMerchandise() {
        // Configura el comportamiento del mock MerchandiseRepository y UserService
        Long validMerchandiseId = 1L;
        Long validUserId = 1L;
        User registeredBy = new User();
        registeredBy.setId(validUserId);
        Merchandise merchandise = new Merchandise();
        merchandise.setId(validMerchandiseId);
        merchandise.setRegisteredBy(registeredBy);
        when(merchandiseRepository.findById(validMerchandiseId)).thenReturn(Optional.of(merchandise));
        doNothing().when(merchandiseRepository).deleteById(validMerchandiseId);

        Long invalidMerchandiseId = 2L;
        when(merchandiseRepository.findById(invalidMerchandiseId)).thenReturn(Optional.empty());

        Long unauthorizedUserId = 3L;

        // Verifica que el método elimina correctamente la mercancía cuando se proporciona un ID de mercancía válido y un ID de usuario válido
        merchandiseService.deleteMerchandise(validMerchandiseId, validUserId);
        verify(merchandiseRepository).deleteById(validMerchandiseId);

        // Verifica que el método lanza una excepción cuando se proporciona un ID de mercancía no válido
        RequestException exception1 = assertThrows(RequestException.class, () -> merchandiseService.deleteMerchandise(invalidMerchandiseId, validUserId));
        assertEquals("The merchandise you provided does not exist", exception1.getMessage());

        // Verifica que el método lanza una excepción cuando se proporciona un ID de usuario no autorizado para eliminar la mercancía
        RequestException exception2 = assertThrows(RequestException.class, () -> merchandiseService.deleteMerchandise(validMerchandiseId, unauthorizedUserId));
        assertEquals("You are not authorized to delete this merchandise", exception2.getMessage());
    }

    @Test
    void testUpdateMerchandise() {
        // Configura los objetos necesarios
        User user = new User();
        user.setId(1L);

        Merchandise merchandise = new Merchandise();
        merchandise.setId(1L);
        merchandise.setProductName("Producto original");
        merchandise.setQuantity(50);
        merchandise.setEntryDate(LocalDate.now().minusDays(1));

        MerchandiseDTO updateMerchandiseDTO = new MerchandiseDTO();
        updateMerchandiseDTO.setProductName("Producto actualizado");
        updateMerchandiseDTO.setQuantity(75);
        updateMerchandiseDTO.setEntryDate(LocalDate.now());
        updateMerchandiseDTO.setEditedById(user.getId());

        // Define el comportamiento de los mocks
        when(merchandiseRepository.findById(anyLong())).thenReturn(Optional.of(merchandise));
        when(userService.findById(anyLong())).thenReturn(user);
        when(merchandiseRepository.save(any(Merchandise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llama al método que se va a probar
        Merchandise updatedMerchandise = merchandiseService.updateMerchandise(1L, updateMerchandiseDTO);

        // Verifica que se haya llamado a los métodos adecuados
        verify(merchandiseRepository).findById(1L);
        verify(userService).findById(updateMerchandiseDTO.getEditedById());
        verify(merchandiseRepository).save(any(Merchandise.class));

        // Verifica que el resultado sea el esperado
        assertNotNull(updatedMerchandise);
        assertEquals("Producto actualizado", updatedMerchandise.getProductName());
        assertEquals(75, updatedMerchandise.getQuantity());
        assertEquals(LocalDate.now(), updatedMerchandise.getEntryDate());
    }

    @Test
    void testFindAllMerchandiseWithEntryDateAndSearchTerm() {
        // Configura los objetos necesarios
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

        // Define el comportamiento de los mocks
        when(merchandiseRepository.findByEntryDateAndSearchTerm(entryDate, searchTerm, pageable)).thenReturn(merchandisePage);

        // Llama al método que se va a probar
        Page<Merchandise> result = merchandiseService.findAllMerchandise(entryDate, searchTerm, pageable);

        // Verifica que se haya llamado a los métodos adecuados
        verify(merchandiseRepository).findByEntryDateAndSearchTerm(entryDate, searchTerm, pageable);

        // Verifica que el resultado sea el esperado
        assertEquals(merchandisePage, result);
    }

    @Test
    void testFindAllMerchandiseWithEntryDate() {
        // Configura los objetos necesarios
        LocalDate entryDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);

        List<Merchandise> merchandiseList = new ArrayList<>();
        Merchandise merchandise = new Merchandise();
        merchandise.setId(1L);
        merchandise.setProductName("Producto");
        merchandise.setQuantity(50);
        merchandise.setEntryDate(entryDate);
        merchandiseList.add(merchandise);

        Page<Merchandise> merchandisePage = new PageImpl<>(merchandiseList);

        // Define el comportamiento de los mocks
        when(merchandiseRepository.findByEntryDate(entryDate, pageable)).thenReturn(merchandisePage);

        // Llama al método que se va a probar
        Page<Merchandise> result = merchandiseService.findAllMerchandise(entryDate, null, pageable);

        // Verifica que se haya llamado a los métodos adecuados
        verify(merchandiseRepository).findByEntryDate(entryDate, pageable);

        // Verifica que el resultado sea el esperado
        assertEquals(merchandisePage, result);
    }

    @Test
    void testFindAllMerchandiseWithSearchTerm() {
        // Configura los objetos necesarios
        String searchTerm = "searchTerm";
        Pageable pageable = PageRequest.of(0, 10);

        List<Merchandise> merchandiseList = new ArrayList<>();
        Merchandise merchandise = new Merchandise();
        merchandise.setId(1L);
        merchandise.setProductName("Producto");
        merchandise.setQuantity(50);
        merchandise.setEntryDate(LocalDate.now());
        merchandiseList.add(merchandise);

        Page<Merchandise> merchandisePage = new PageImpl<>(merchandiseList);

        // Define el comportamiento de los mocks
        when(merchandiseRepository.findBySearchTerm(searchTerm, pageable)).thenReturn(merchandisePage);

        // Llama al método que se va a probar
        Page<Merchandise> result = merchandiseService.findAllMerchandise(null, searchTerm, pageable);

        // Verifica que se haya llamado a los métodos adecuados
        verify(merchandiseRepository).findBySearchTerm(searchTerm, pageable);

        // Verifica que el resultado sea el esperado
        assertEquals(merchandisePage, result);
    }

    @Test
    void testFindAllMerchandiseWithoutFilters() {
        // Configura los objetos necesarios
        Pageable pageable = PageRequest.of(0, 10);

        List<Merchandise> merchandiseList = new ArrayList<>();
        Merchandise merchandise = new Merchandise();
        merchandise.setId(1L);
        merchandise.setProductName("Producto");
        merchandise.setQuantity(50);
        merchandise.setEntryDate(LocalDate.now());
        merchandiseList.add(merchandise);

        Page<Merchandise> merchandisePage = new PageImpl<>(merchandiseList);

        // Define el comportamiento de los mocks
        when(merchandiseRepository.findAll(pageable)).thenReturn(merchandisePage);

        // Llama al método que se va a probar
        Page<Merchandise> result = merchandiseService.findAllMerchandise(null, null, pageable);

        // Verifica que se haya llamado a los métodos adecuados
        verify(merchandiseRepository).findAll(pageable);

        // Verifica que el resultado sea el esperado
        assertEquals(merchandisePage, result);
    }

    @Test
    void testFindAllMerchandiseByEntryDateAndSearchTermNoResults() {
        // Configura los objetos necesarios
        LocalDate entryDate = LocalDate.now();
        String searchTerm = "searchTerm";
        Pageable pageable = PageRequest.of(0, 10);

        // Define un resultado vacío
        Page<Merchandise> emptyPage = new PageImpl<>(Collections.emptyList());

        // Define el comportamiento de los mocks
        when(merchandiseRepository.findByEntryDateAndSearchTerm(entryDate, searchTerm, pageable)).thenReturn(emptyPage);

        // Llama al método que se va a probar y espera una excepción
        assertThrows(RequestException.class, () -> merchandiseService.findAllMerchandise(entryDate, searchTerm, pageable));
    }

    @Test
    void testFindAllMerchandiseWithPageableNoResults() {
        // Configura los objetos necesarios
        Pageable pageable = PageRequest.of(0, 10);

        // Define un resultado vacío
        Page<Merchandise> emptyPage = new PageImpl<>(Collections.emptyList());

        // Define el comportamiento de los mocks
        when(merchandiseRepository.findAll(pageable)).thenReturn(emptyPage);

        // Llama al método que se va a probar y espera una excepción
        assertThrows(RequestException.class, () -> merchandiseService.findAllMerchandise(null, null, pageable));
    }

    @Test
    void testFindAllMerchandiseByEntryDateNoResults() {
        // Configura los objetos necesarios
        LocalDate entryDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 10);

        // Define un resultado vacío
        Page<Merchandise> emptyPage = new PageImpl<>(Collections.emptyList());

        // Define el comportamiento de los mocks
        when(merchandiseRepository.findByEntryDate(entryDate, pageable)).thenReturn(emptyPage);

        // Llama al método que se va a probar y espera una excepción
        assertThrows(RequestException.class, () -> merchandiseService.findAllMerchandise(entryDate, null, pageable));
    }

    @Test
    void testFindAllMerchandiseBySearchTermNoResults() {
        // Configura los objetos necesarios
        String searchTerm = "searchTerm";
        Pageable pageable = PageRequest.of(0, 10);

        // Define un resultado vacío
        Page<Merchandise> emptyPage = new PageImpl<>(Collections.emptyList());

        // Define el comportamiento de los mocks
        when(merchandiseRepository.findBySearchTerm(searchTerm, pageable)).thenReturn(emptyPage);

        // Llama al método que se va a probar y espera una excepción
        assertThrows(RequestException.class, () -> merchandiseService.findAllMerchandise(null, searchTerm, pageable));
    }


}
