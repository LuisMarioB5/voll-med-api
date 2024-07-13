package com.bonidev.api.dto.medico;

import com.bonidev.api.model.entity.MedicoEntity;

public record MostrarMedicoDTO(
        Long id,

        String nombre,

        String email,

        String documento,

        String especialidad) {
    public MostrarMedicoDTO(MedicoEntity entity) {
        this(entity.getId(),
                entity.getNombre(),
                entity.getEmail(),
                entity.getDocumento(),
                String.valueOf(entity.getEspecialidad()));
    }
}
