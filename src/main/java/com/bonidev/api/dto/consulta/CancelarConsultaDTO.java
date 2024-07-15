package com.bonidev.api.dto.consulta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CancelarConsultaDTO(
        Long consultaId,

        String motivoCancelacion) {
}
