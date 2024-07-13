package com.bonidev.api.dto.paciente;

import com.bonidev.api.model.entity.DireccionEntity;
import com.bonidev.api.model.entity.PacienteEntity;

public record DetallesPacienteDTO(
        Long id,

        String nombre,

        String email,

        String telefono,

        String documento,

        DireccionEntity direccion) {

    public DetallesPacienteDTO(PacienteEntity paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumento(), paciente.getDireccion());
    }
}
