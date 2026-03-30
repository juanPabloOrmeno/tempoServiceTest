package org.bank.temposervice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de Tenpista")
public record TenpistaResponse(

        @Schema(description = "ID del Tenpista", example = "1")
        Long id,

        @Schema(description = "Nombre del Tenpista", example = "Juan Pérez")
        String name
) {}