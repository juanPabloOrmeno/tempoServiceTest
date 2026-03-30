package org.bank.temposervice.controller;

import org.bank.temposervice.dto.request.TransactionRequest;
import org.bank.temposervice.dto.response.TransactionResponse;
import org.bank.temposervice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@DisplayName("TransactionController Tests")
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private static final LocalDateTime NOW = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create transaction with valid request")
    void testCreateTransaction_Success() {
        // Arrange
        TransactionRequest request = new TransactionRequest(
                100,
                500,
                "Amazon",
                1L,
                NOW
        );
        TransactionResponse response = new TransactionResponse(
                100,
                500,
                "Amazon",
                "Juan Pérez",
                NOW,
                NOW
        );
        when(transactionService.createTransaction(any(TransactionRequest.class)))
                .thenReturn(response);

        // Act
        var result = transactionController.createTransaction(request);

        // Assert
        assertNotNull(result);
        assertEquals(201, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(100, result.getBody().transactionId());
        assertEquals(500, result.getBody().amount());
        verify(transactionService, times(1)).createTransaction(any(TransactionRequest.class));
    }

    @Test
    @DisplayName("Should get all transactions successfully")
    void testGetAllTransactions_Success() {
        // Arrange
        TransactionResponse response1 = new TransactionResponse(100, 500, "Amazon", "Juan Pérez", NOW, NOW);
        TransactionResponse response2 = new TransactionResponse(101, 1000, "Ebay", "María García", NOW, NOW);
        List<TransactionResponse> responses = Arrays.asList(response1, response2);

        when(transactionService.getAllTransactions()).thenReturn(responses);

        // Act
        var result = transactionController.getAllTransactions();

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    @DisplayName("Should return empty list when no transactions exist")
    void testGetAllTransactions_EmptyList() {
        // Arrange
        when(transactionService.getAllTransactions()).thenReturn(List.of());

        // Act
        var result = transactionController.getAllTransactions();

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    @DisplayName("Should get transaction by id successfully")
    void testGetTransactionById_Success() {
        // Arrange
        TransactionResponse response = new TransactionResponse(
                100,
                500,
                "Amazon",
                "Juan Pérez",
                NOW,
                NOW
        );
        when(transactionService.getTransactionById(100)).thenReturn(response);

        // Act
        var result = transactionController.getTransactionById(100);

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(100, result.getBody().transactionId());
        assertEquals(500, result.getBody().amount());
        verify(transactionService, times(1)).getTransactionById(100);
    }

    @Test
    @DisplayName("Should throw exception when transaction not found by id")
    void testGetTransactionById_NotFound() {
        // Arrange
        when(transactionService.getTransactionById(anyInt()))
                .thenThrow(new RuntimeException("Transaction not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> transactionController.getTransactionById(999));
        verify(transactionService, times(1)).getTransactionById(999);
    }
}
