package com.bonidev.api.dto.consulta;

import com.bonidev.api.model.Especialidad;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendarConsultaDTO(
        Long id,

        @NotNull
        Long idPaciente,

        Long idMedico,

        Especialidad especialidad,

        @NotNull
        @Future
        LocalDateTime fecha,

        Boolean estaActiva) {}
