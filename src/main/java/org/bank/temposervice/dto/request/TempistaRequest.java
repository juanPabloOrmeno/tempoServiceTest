package org.bank.temposervice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TempistaRequest(

        @NotBlank(message = "name is required")
        String name
) {}
