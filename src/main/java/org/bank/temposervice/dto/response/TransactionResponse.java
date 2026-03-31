package org.bank.temposervice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Respuesta de una transacción")
public record TransactionResponse(

        @Schema(description = "ID de negocio de la transacción", example = "1001")
        Integer transactionId,

        @Schema(description = "Monto de la transacción en pesos", example = "15000")
        Integer amount,

        @Schema(description = "Giro o comercio de la transacción", example = "Supermercado Lider")
        String merchant,

        @Schema(description = "Nombre del Tempista asociado", example = "Juan Pérez")
        String tempistaName,

        @Schema(description = "Fecha de la transacción", example = "2026-03-28T15:30:00")
        LocalDateTime transactionDate,

        @Schema(description = "Fecha de creación del registro", example = "2026-03-28T15:31:00")
        LocalDateTime createdAt
) {}