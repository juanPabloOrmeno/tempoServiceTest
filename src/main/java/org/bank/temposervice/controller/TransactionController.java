package org.bank.temposervice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.bank.temposervice.dto.request.TransactionRequest;
import org.bank.temposervice.dto.response.TransactionResponse;
import org.bank.temposervice.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@Validated
@Tag(name = "Transactions", description = "API para gestión de transacciones de Tenpistas")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva transacción",
            description = "Registra una nueva transacción asociada a un Tenpista"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Transacción creada exitosamente",
                    content = @Content(schema = @Schema(implementation = TransactionResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida o error de validación",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe una transacción con ese transactionId",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    public ResponseEntity<TransactionResponse> createTransaction(
            @Parameter(description = "Datos de la transacción", required = true)
            @Valid @RequestBody TransactionRequest request
    ) {
        TransactionResponse response = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Listar transacciones",
            description = "Obtiene el listado de transacciones ordenadas por fecha descendente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido exitosamente",
                    content = @Content(schema = @Schema(implementation = TransactionResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<TransactionResponse> response = transactionService.getAllTransactions();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{transactionId}")
    @Operation(
            summary = "Obtener transacción por transactionId",
            description = "Busca una transacción específica por su identificador de negocio"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Transacción encontrada",
                    content = @Content(schema = @Schema(implementation = TransactionResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "transactionId inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transacción no encontrada",
                    content = @Content
            )
    })
    public ResponseEntity<TransactionResponse> getTransactionById(
            @Parameter(description = "ID de negocio de la transacción", required = true, example = "1001")
            @PathVariable @Positive(message = "transactionId must be greater than 0") Integer transactionId
    ) {
        TransactionResponse response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(response);
    }
}