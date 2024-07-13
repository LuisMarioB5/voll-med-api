package com.bonidev.api.dto.paciente;

import com.bonidev.api.model.entity.PacienteEntity;

public record ListaPacienteDTO(
        Long id,

        String nombre,

        String email,

        String documento) {

    public ListaPacienteDTO(PacienteEntity paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumento());
    }
}
