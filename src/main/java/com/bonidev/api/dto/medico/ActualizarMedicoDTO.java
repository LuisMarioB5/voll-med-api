package com.bonidev.api.dto.medico;

import com.bonidev.api.model.entity.DireccionEntity;
import jakarta.validation.constraints.NotNull;

public record ActualizarMedicoDTO(
        @NotNull
        Long id,

        String nombre,

        String documento,

        DireccionEntity direccion
) {
}
