package com.bonidev.api.dto.paciente;

import com.bonidev.api.model.entity.DireccionEntity;
import jakarta.validation.constraints.NotNull;

public record ActualizarPacienteDTO(
        @NotNull
        Long id,

        String nombre,

        String telefono,

        DireccionEntity direccionActualizarDTO) {}
