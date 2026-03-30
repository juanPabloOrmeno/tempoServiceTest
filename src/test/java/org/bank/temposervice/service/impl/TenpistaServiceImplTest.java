package org.bank.temposervice.service.impl;

import org.bank.temposervice.dto.request.TenpistaRequest;
import org.bank.temposervice.dto.response.TenpistaResponse;
import org.bank.temposervice.entity.Tenpista;
import org.bank.temposervice.repository.TenpistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TenpistaServiceImpl Tests")
class TenpistaServiceImplTest {

    @Mock
    private TenpistaRepository tenpistaRepository;

    @InjectMocks
    private TenpistaServiceImpl tenpistaService;

    private Tenpista tenpista;
    private TenpistaRequest tenpistaRequest;

    @BeforeEach
    void setUp() {
        tenpista = new Tenpista();
        tenpista.setId(1L);
        tenpista.setName("Juan Pérez");

        tenpistaRequest = new TenpistaRequest("Juan Pérez");
    }

    @Test
    @DisplayName("Should get all tenpistas successfully")
    void testGetAllTenpistas_Success() {
        // Arrange
        Tenpista tenpista2 = new Tenpista();
        tenpista2.setId(2L);
        tenpista2.setName("María García");

        List<Tenpista> tenpistas = Arrays.asList(tenpista, tenpista2);
        when(tenpistaRepository.findAll()).thenReturn(tenpistas);

        // Act
        List<TenpistaResponse> result = tenpistaService.getAllTenpistas();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan Pérez", result.get(0).name());
        assertEquals("María García", result.get(1).name());
        verify(tenpistaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no tenpistas exist")
    void testGetAllTenpistas_EmptyList() {
        // Arrange
        when(tenpistaRepository.findAll()).thenReturn(List.of());

        // Act
        List<TenpistaResponse> result = tenpistaService.getAllTenpistas();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(tenpistaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get tenpista by name successfully")
    void testGetByName_Success() {
        // Arrange
        when(tenpistaRepository.findByName("Juan Pérez")).thenReturn(Optional.of(tenpista));

        // Act
        TenpistaResponse result = tenpistaService.getByName("Juan Pérez");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Juan Pérez", result.name());
        verify(tenpistaRepository, times(1)).findByName("Juan Pérez");
    }

    @Test
    @DisplayName("Should throw exception when tenpista not found by name")
    void testGetByName_NotFound() {
        // Arrange
        when(tenpistaRepository.findByName("Non-existent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tenpistaService.getByName("Non-existent"));
        verify(tenpistaRepository, times(1)).findByName("Non-existent");
    }

    @Test
    @DisplayName("Should create tenpista successfully")
    void testCreate_Success() {
        // Arrange
        when(tenpistaRepository.save(any(Tenpista.class))).thenReturn(tenpista);

        // Act
        TenpistaResponse result = tenpistaService.create(tenpistaRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Juan Pérez", result.name());
        verify(tenpistaRepository, times(1)).save(any(Tenpista.class));
    }

    @Test
    @DisplayName("Should map tenpista to response correctly")
    void testMapToResponse() {
        // Arrange
        when(tenpistaRepository.save(any(Tenpista.class))).thenReturn(tenpista);

        // Act
        TenpistaResponse result = tenpistaService.create(tenpistaRequest);

        // Assert
        assertEquals(tenpista.getId(), result.id());
        assertEquals(tenpista.getName(), result.name());
    }
}
