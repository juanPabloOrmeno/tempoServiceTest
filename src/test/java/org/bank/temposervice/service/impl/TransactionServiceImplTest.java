package org.bank.temposervice.service.impl;

import org.bank.temposervice.dto.request.TransactionRequest;
import org.bank.temposervice.dto.response.TransactionResponse;
import org.bank.temposervice.entity.Tempista;
import org.bank.temposervice.model.Transaction;
import org.bank.temposervice.repository.TempistaRepository;
import org.bank.temposervice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionServiceImpl Tests")
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TempistaRepository tempistaRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private Tempista tempista;
    private TransactionRequest transactionRequest;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        tempista = new Tempista();
        tempista.setId(1L);
        tempista.setName("Juan Pérez");

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setTransactionId(100);
        transaction.setAmount(500);
        transaction.setMerchant("Amazon");
        transaction.setTempista(tempista);
        transaction.setTransactionDate(now);
        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);

        transactionRequest = new TransactionRequest(
                100,
                500,
                "Amazon",
                1L,
                now
        );
    }

    @Test
    @DisplayName("Should create transaction successfully")
    void testCreateTransaction_Success() {
        // Arrange
        when(tempistaRepository.findById(1L)).thenReturn(Optional.of(tempista));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        TransactionResponse result = transactionService.createTransaction(transactionRequest);

        // Assert
        assertNotNull(result);
        assertEquals(100, result.transactionId());
        assertEquals(500, result.amount());
        assertEquals("Amazon", result.merchant());
        assertEquals("Juan Pérez", result.tempistaName());
        verify(tempistaRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw exception when tempista not found for transaction")
    void testCreateTransaction_TempistaNotFound() {
        // Arrange
        when(tempistaRepository.findById(999L)).thenReturn(Optional.empty());

        TransactionRequest request = new TransactionRequest(
                100,
                500,
                "Amazon",
                999L,
                now
        );

        // Act & Assert
        assertThrows(RuntimeException.class, () -> transactionService.createTransaction(request));
        verify(tempistaRepository, times(1)).findById(999L);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should get all transactions successfully")
    void testGetAllTransactions_Success() {
        // Arrange
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setTransactionId(101);
        transaction2.setAmount(1000);
        transaction2.setMerchant("Ebay");
        transaction2.setTempista(tempista);
        transaction2.setTransactionDate(now);
        transaction2.setCreatedAt(now);
        transaction2.setUpdatedAt(now);

        List<Transaction> transactions = Arrays.asList(transaction, transaction2);
        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        List<TransactionResponse> result = transactionService.getAllTransactions();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(100, result.get(0).transactionId());
        assertEquals(101, result.get(1).transactionId());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no transactions exist")
    void testGetAllTransactions_EmptyList() {
        // Arrange
        when(transactionRepository.findAll()).thenReturn(List.of());

        // Act
        List<TransactionResponse> result = transactionService.getAllTransactions();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get transaction by id successfully")
    void testGetTransactionById_Success() {
        // Arrange
        when(transactionRepository.findByTransactionId(100)).thenReturn(Optional.of(transaction));

        // Act
        TransactionResponse result = transactionService.getTransactionById(100);

        // Assert
        assertNotNull(result);
        assertEquals(100, result.transactionId());
        assertEquals(500, result.amount());
        assertEquals("Amazon", result.merchant());
        verify(transactionRepository, times(1)).findByTransactionId(100);
    }

    @Test
    @DisplayName("Should throw exception when transaction not found by id")
    void testGetTransactionById_NotFound() {
        // Arrange
        when(transactionRepository.findByTransactionId(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> transactionService.getTransactionById(999));
        verify(transactionRepository, times(1)).findByTransactionId(999);
    }

    @Test
    @DisplayName("Should handle transaction with null tempista")
    void testGetTransactionById_NullTempista() {
        // Arrange
        Transaction txWithoutTempista = new Transaction();
        txWithoutTempista.setId(3L);
        txWithoutTempista.setTransactionId(102);
        txWithoutTempista.setAmount(250);
        txWithoutTempista.setMerchant("Store");
        txWithoutTempista.setTempista(null);
        txWithoutTempista.setTransactionDate(now);
        txWithoutTempista.setCreatedAt(now);
        txWithoutTempista.setUpdatedAt(now);

        when(transactionRepository.findByTransactionId(102)).thenReturn(Optional.of(txWithoutTempista));

        // Act
        TransactionResponse result = transactionService.getTransactionById(102);

        // Assert
        assertNotNull(result);
        assertNull(result.tempistaName());
        assertEquals(102, result.transactionId());
    }
}
