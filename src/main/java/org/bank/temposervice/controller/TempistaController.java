package org.bank.temposervice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.bank.temposervice.dto.request.TempistaRequest;
import org.bank.temposervice.dto.response.TempistaResponse;
import org.bank.temposervice.service.TempistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tempistas")
@Validated
@Tag(name = "Tempistas", description = "API para gestión de Tempistas")
public class TempistaController {

    private final TempistaService tempistaService;

    public TempistaController(TempistaService tempistaService) {
        this.tempistaService = tempistaService;
    }

    @GetMapping
    @Operation(
            summary = "Listar Tempistas",
            description = "Obtiene el listado de todos los Tempistas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado obtenido exitosamente",
                    content = @Content(schema = @Schema(implementation = TempistaResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    public ResponseEntity<List<TempistaResponse>> getAllTempistas() {
        List<TempistaResponse> response = tempistaService.getAllTempistas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Buscar Tempista por nombre",
            description = "Obtiene un Tempista por su nombre"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tempista encontrado",
                    content = @Content(schema = @Schema(implementation = TempistaResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tempista no encontrado",
                    content = @Content
            )
    })
    public ResponseEntity<TempistaResponse> getByName(
            @Parameter(description = "Nombre del Tempista", required = true, example = "Juan Pérez")
            @PathVariable @NotBlank(message = "Name is required") String name
    ) {
        TempistaResponse response = tempistaService.getByName(name);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
            summary = "Crear Tempista",
            description = "Crea un nuevo Tempista"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tempista creado exitosamente",
                    content = @Content(schema = @Schema(implementation = TempistaResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    public ResponseEntity<TempistaResponse> createTempista(
            @Valid @RequestBody TempistaRequest request
    ) {
        TempistaResponse response = tempistaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar Tempista",
            description = "Elimina un Tempista por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Tempista eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tempista no encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteTempista(
            @Parameter(description = "ID del Tempista", required = true, example = "1")
            @PathVariable Long id
    ) {
        tempistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}