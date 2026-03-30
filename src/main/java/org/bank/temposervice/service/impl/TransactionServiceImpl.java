package org.bank.temposervice.service.impl;

import org.bank.temposervice.dto.request.TransactionRequest;
import org.bank.temposervice.dto.response.TransactionResponse;
import org.bank.temposervice.entity.Tenpista;
import org.bank.temposervice.model.Transaction;

import org.bank.temposervice.repository.TenpistaRepository;
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
    private final TenpistaRepository tenpistaRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                 TenpistaRepository tenpistaRepository) {
        this.transactionRepository = transactionRepository;
        this.tenpistaRepository = tenpistaRepository;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        log.info("Creating transaction with transactionId: {}", request.transactionId());

        // Validar que el tenpista existe
        Tenpista tenpista = tenpistaRepository.findById(request.tenpistaId())
                .orElseThrow(() -> new RuntimeException("Tenpista not found with id: " + request.tenpistaId()));

        Transaction transaction = new Transaction();
        transaction.setTransactionId(request.transactionId());
        transaction.setAmount(request.amount());
        transaction.setMerchant(request.merchant());
        transaction.setTenpista(tenpista);
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

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getAmount(),
                transaction.getMerchant(),
                transaction.getTenpista() != null ? transaction.getTenpista().getName() : null,
                transaction.getTransactionDate(),
                transaction.getCreatedAt()
        );
    }
}