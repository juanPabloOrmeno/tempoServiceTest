package org.bank.temposervice.service;

import org.bank.temposervice.dto.request.TransactionRequest;
import org.bank.temposervice.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest request);

    List<TransactionResponse> getAllTransactions();

    TransactionResponse getTransactionById(Integer transactionId);

    void deleteTransaction(Integer transactionId);
}