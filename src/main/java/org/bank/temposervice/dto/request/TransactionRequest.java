package org.bank.temposervice.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record TransactionRequest(

        @NotNull(message = "transactionId is required")
        @Positive(message = "transactionId must be greater than 0")
        Integer transactionId,

        @NotNull(message = "amount is required")
        @Positive(message = "amount must be greater than 0")
        Integer amount,

        @NotBlank(message = "merchant is required")
        String merchant,

        @NotNull(message = "tempistaId is required")
        @Positive(message = "tempistaId must be greater than 0")
        Long tempistaId,

        @NotNull(message = "transactionDate is required")
        LocalDateTime transactionDate
) {}