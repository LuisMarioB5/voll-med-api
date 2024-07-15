package com.bonidev.api.dto.consulta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CancelarConsultaDTO(
        @NotNull
        Long consultaId,

        @NotBlank
        String motivoCancelacion) {}
