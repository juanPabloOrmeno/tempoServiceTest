package org.bank.temposervice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.bank.temposervice.dto.response.TenpistaResponse;
import org.bank.temposervice.service.TenpistaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenpistas")
@Validated
@Tag(name = "Tenpistas", description = "API para gestión de Tenpistas")
public class TenpistaController {

    private final TenpistaService tenpistaService;

    public TenpistaController(TenpistaService tenpistaService) {
        this.tenpistaService = tenpistaService;
    }

    @GetMapping
    @Operation(
            summary = "Listar Tenpistas",
            description = "Obtiene el listado de todos los Tenpistas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido exitosamente",
                    content = @Content(schema = @Schema(implementation = TenpistaResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    public ResponseEntity<List<TenpistaResponse>> getAllTenpistas() {
        List<TenpistaResponse> response = tenpistaService.getAllTenpistas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Buscar Tenpista por nombre",
            description = "Obtiene un Tenpista por su nombre"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tenpista encontrado",
                    content = @Content(schema = @Schema(implementation = TenpistaResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tenpista no encontrado",
                    content = @Content
            )
    })
    public ResponseEntity<TenpistaResponse> getByName(
            @Parameter(description = "Nombre del Tenpista", required = true, example = "Juan Pérez")
            @PathVariable @NotBlank(message = "Name is required") String name
    ) {
        TenpistaResponse response = tenpistaService.getByName(name);
        return ResponseEntity.ok(response);
    }
}