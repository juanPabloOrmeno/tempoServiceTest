package org.bank.temposervice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de Tempista")
public record TempistaResponse(

        @Schema(description = "ID del Tempista", example = "1")
        Long id,

        @Schema(description = "Nombre del Tempista", example = "Juan Pérez")
        String name
) {}