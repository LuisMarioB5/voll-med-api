package com.bonidev.api.dto.paciente;

import com.bonidev.api.dto.direccion.DireccionDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record RegistrarPacienteDTO(
        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 0, max = 15)
        String telefono,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String documento,

        @NotNull
        @Valid
        DireccionDTO direccion) {}
