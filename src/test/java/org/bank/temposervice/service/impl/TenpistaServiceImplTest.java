package org.bank.temposervice.service.impl;

import org.bank.temposervice.dto.request.TempistaRequest;
import org.bank.temposervice.dto.response.TempistaResponse;
import org.bank.temposervice.entity.Tempista;
import org.bank.temposervice.repository.TempistaRepository;
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
@DisplayName("TempistaServiceImpl Tests")
class TempistaServiceImplTest {

    @Mock
    private TempistaRepository tempistaRepository;

    @InjectMocks
    private TempistaServiceImpl tempistaService;

    private Tempista tempista;
    private TempistaRequest tempistaRequest;

    @BeforeEach
    void setUp() {
        tempista = new Tempista();
        tempista.setId(1L);
        tempista.setName("Juan Pérez");

        tempistaRequest = new TempistaRequest("Juan Pérez");
    }

    @Test
    @DisplayName("Should get all tempistas successfully")
    void testGetAllTempistas_Success() {
        // Arrange
        Tempista tempista2 = new Tempista();
        tempista2.setId(2L);
        tempista2.setName("María García");

        List<Tempista> tempistas = Arrays.asList(tempista, tempista2);
        when(tempistaRepository.findAll()).thenReturn(tempistas);

        // Act
        List<TempistaResponse> result = tempistaService.getAllTempistas();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan Pérez", result.get(0).name());
        assertEquals("María García", result.get(1).name());
        verify(tempistaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no tempistas exist")
    void testGetAllTempistas_EmptyList() {
        // Arrange
        when(tempistaRepository.findAll()).thenReturn(List.of());

        // Act
        List<TempistaResponse> result = tempistaService.getAllTempistas();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(tempistaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get tempista by name successfully")
    void testGetByName_Success() {
        // Arrange
        when(tempistaRepository.findByName("Juan Pérez")).thenReturn(Optional.of(tempista));

        // Act
        TempistaResponse result = tempistaService.getByName("Juan Pérez");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Juan Pérez", result.name());
        verify(tempistaRepository, times(1)).findByName("Juan Pérez");
    }

    @Test
    @DisplayName("Should throw exception when tempista not found by name")
    void testGetByName_NotFound() {
        // Arrange
        when(tempistaRepository.findByName("Non-existent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tempistaService.getByName("Non-existent"));
        verify(tempistaRepository, times(1)).findByName("Non-existent");
    }

    @Test
    @DisplayName("Should create tempista successfully")
    void testCreate_Success() {
        // Arrange
        when(tempistaRepository.save(any(Tempista.class))).thenReturn(tempista);

        // Act
        TempistaResponse result = tempistaService.create(tempistaRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Juan Pérez", result.name());
        verify(tempistaRepository, times(1)).save(any(Tempista.class));
    }

    @Test
    @DisplayName("Should map tempista to response correctly")
    void testMapToResponse() {
        // Arrange
        when(tempistaRepository.save(any(Tempista.class))).thenReturn(tempista);

        // Act
        TempistaResponse result = tempistaService.create(tempistaRequest);

        // Assert
        assertEquals(tempista.getId(), result.id());
        assertEquals(tempista.getName(), result.name());
    }
}
