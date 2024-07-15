package com.bonidev.api.dto.consulta;

import com.bonidev.api.model.entity.ConsultaEntity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DetalleConsultaDTO(
        Long id,

        @NotNull
        Long idPaciente,

        Long idMedico,

        @NotNull
        @Future
        LocalDateTime fecha) {
    public DetalleConsultaDTO(ConsultaEntity consulta) {
        this(consulta.getId(), consulta.getPacienteEntity().getId(), consulta.getMedicoEntity().getId(), consulta.getFechaConsulta());
    }
}
