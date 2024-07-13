package com.bonidev.api.dto.security;

import jakarta.validation.constraints.NotBlank;

public record AutenticacionDTO(
        @NotBlank
        String login,

        @NotBlank
        String clave
) {}
