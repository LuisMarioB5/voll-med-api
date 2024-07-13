package com.bonidev.api.dto.medico;

import com.bonidev.api.dto.direccion.DireccionDTO;
import com.bonidev.api.model.Especialidad;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegistrarMedicoDTO(
        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String documento,

        @NotNull
        Especialidad especialidad,

        @NotBlank
        String telefono,

        @NotNull
        DireccionDTO direccion) {
}
