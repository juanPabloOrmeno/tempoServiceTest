package org.bank.temposervice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TenpistaRequest(

        @NotBlank(message = "name is required")
        String name
) {}
