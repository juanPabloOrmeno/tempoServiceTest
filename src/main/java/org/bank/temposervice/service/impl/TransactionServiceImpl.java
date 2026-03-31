package org.bank.temposervice.service.impl;

import org.bank.temposervice.dto.request.TransactionRequest;
import org.bank.temposervice.dto.response.TransactionResponse;
import org.bank.temposervice.entity.Tempista;
import org.bank.temposervice.model.Transaction;

import org.bank.temposervice.repository.TempistaRepository;
import org.bank.temposervice.repository.TransactionRepository;
import org.bank.temposervice.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;
    private final TempistaRepository tempistaRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                 TempistaRepository tempistaRepository) {
        this.transactionRepository = transactionRepository;
        this.tempistaRepository = tempistaRepository;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        log.info("Creating transaction with transactionId: {}", request.transactionId());

        // Validar que el tempista existe
        Tempista tempista = tempistaRepository.findById(request.tempistaId())
                .orElseThrow(() -> new RuntimeException("Tempista not found with id: " + request.tempistaId()));

        Transaction transaction = new Transaction();
        transaction.setTransactionId(request.transactionId());
        transaction.setAmount(request.amount());
        transaction.setMerchant(request.merchant());
        transaction.setTempista(tempista);
        transaction.setTransactionDate(request.transactionDate());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        log.info("Getting all transactions");

        return transactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TransactionResponse getTransactionById(Integer transactionId) {
        log.info("Getting transaction by transactionId: {}", transactionId);

        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with transactionId: " + transactionId));

        return mapToResponse(transaction);
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        log.info("Deleting transaction with transactionId: {}", transactionId);

        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with transactionId: " + transactionId));

        transactionRepository.delete(transaction);
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getAmount(),
                transaction.getMerchant(),
                transaction.getTempista() != null ? transaction.getTempista().getName() : null,
                transaction.getTransactionDate(),
                transaction.getCreatedAt()
        );
    }
}