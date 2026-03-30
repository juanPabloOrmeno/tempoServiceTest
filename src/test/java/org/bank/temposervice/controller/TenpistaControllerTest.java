package org.bank.temposervice.controller;

import org.bank.temposervice.dto.request.TenpistaRequest;
import org.bank.temposervice.dto.response.TenpistaResponse;
import org.bank.temposervice.service.TenpistaService;
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

@DisplayName("TenpistaController Tests")
class TenpistaControllerTest {

    @Mock
    private TenpistaService tenpistaService;

    @InjectMocks
    private TenpistaController tenpistaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get all tenpistas successfully")
    void testGetAllTenpistas_Success() {
        // Arrange
        TenpistaResponse response1 = new TenpistaResponse(1L, "Juan Pérez");
        TenpistaResponse response2 = new TenpistaResponse(2L, "María García");
        List<TenpistaResponse> responses = Arrays.asList(response1, response2);

        when(tenpistaService.getAllTenpistas()).thenReturn(responses);

        // Act
        var result = tenpistaController.getAllTenpistas();

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        verify(tenpistaService, times(1)).getAllTenpistas();
    }

    @Test
    @DisplayName("Should return empty list when no tenpistas exist")
    void testGetAllTenpistas_EmptyList() {
        // Arrange
        when(tenpistaService.getAllTenpistas()).thenReturn(List.of());

        // Act
        var result = tenpistaController.getAllTenpistas();

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        verify(tenpistaService, times(1)).getAllTenpistas();
    }

    @Test
    @DisplayName("Should get tenpista by name successfully")
    void testGetByName_Success() {
        // Arrange
        TenpistaResponse response = new TenpistaResponse(1L, "Juan Pérez");
        when(tenpistaService.getByName("Juan Pérez")).thenReturn(response);

        // Act
        var result = tenpistaController.getByName("Juan Pérez");

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().id());
        assertEquals("Juan Pérez", result.getBody().name());
        verify(tenpistaService, times(1)).getByName("Juan Pérez");
    }

    @Test
    @DisplayName("Should throw exception when tenpista not found by name")
    void testGetByName_NotFound() {
        // Arrange
        when(tenpistaService.getByName(anyString()))
                .thenThrow(new RuntimeException("Tenpista not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tenpistaController.getByName("Non-existent"));
        verify(tenpistaService, times(1)).getByName("Non-existent");
    }

    @Test
    @DisplayName("Should create tenpista successfully")
    void testCreateTenpista_Success() {
        // Arrange
        TenpistaRequest request = new TenpistaRequest("Juan Pérez");
        TenpistaResponse response = new TenpistaResponse(1L, "Juan Pérez");
        when(tenpistaService.create(any(TenpistaRequest.class))).thenReturn(response);

        // Act
        var result = tenpistaController.createTenpista(request);

        // Assert
        assertNotNull(result);
        assertEquals(201, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().id());
        assertEquals("Juan Pérez", result.getBody().name());
        verify(tenpistaService, times(1)).create(any(TenpistaRequest.class));
    }
}
