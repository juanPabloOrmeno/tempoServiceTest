package org.bank.temposervice.controller;

import org.bank.temposervice.dto.request.TempistaRequest;
import org.bank.temposervice.dto.response.TempistaResponse;
import org.bank.temposervice.service.TempistaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("TempistaController Tests")
class TempistaControllerTest {

    @Mock
    private TempistaService tempistaService;

    @InjectMocks
    private TempistaController tempistaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get all tempistas successfully")
    void testGetAllTempistas_Success() {
        // Arrange
        TempistaResponse response1 = new TempistaResponse(1L, "Juan Pérez");
        TempistaResponse response2 = new TempistaResponse(2L, "María García");
        List<TempistaResponse> responses = Arrays.asList(response1, response2);

        when(tempistaService.getAllTempistas()).thenReturn(responses);

        // Act
        var result = tempistaController.getAllTempistas();

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        verify(tempistaService, times(1)).getAllTempistas();
    }

    @Test
    @DisplayName("Should return empty list when no tempistas exist")
    void testGetAllTempistas_EmptyList() {
        // Arrange
        when(tempistaService.getAllTempistas()).thenReturn(List.of());

        // Act
        var result = tempistaController.getAllTempistas();

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        verify(tempistaService, times(1)).getAllTempistas();
    }

    @Test
    @DisplayName("Should get tempista by name successfully")
    void testGetByName_Success() {
        // Arrange
        TempistaResponse response = new TempistaResponse(1L, "Juan Pérez");
        when(tempistaService.getByName("Juan Pérez")).thenReturn(response);

        // Act
        var result = tempistaController.getByName("Juan Pérez");

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().id());
        assertEquals("Juan Pérez", result.getBody().name());
        verify(tempistaService, times(1)).getByName("Juan Pérez");
    }

    @Test
    @DisplayName("Should throw exception when tempista not found by name")
    void testGetByName_NotFound() {
        // Arrange
        when(tempistaService.getByName(anyString()))
                .thenThrow(new RuntimeException("Tempista not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tempistaController.getByName("Non-existent"));
        verify(tempistaService, times(1)).getByName("Non-existent");
    }

    @Test
    @DisplayName("Should create tempista successfully")
    void testCreateTempista_Success() {
        // Arrange
        TempistaRequest request = new TempistaRequest("Juan Pérez");
        TempistaResponse response = new TempistaResponse(1L, "Juan Pérez");
        when(tempistaService.create(any(TempistaRequest.class))).thenReturn(response);

        // Act
        var result = tempistaController.createTempista(request);

        // Assert
        assertNotNull(result);
        assertEquals(201, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().id());
        assertEquals("Juan Pérez", result.getBody().name());
        verify(tempistaService, times(1)).create(any(TempistaRequest.class));
    }
}
