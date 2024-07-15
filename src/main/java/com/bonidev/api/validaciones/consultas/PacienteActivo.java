package com.bonidev.api.validaciones.consultas;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.repository.PacienteRepository;
import com.bonidev.api.validaciones.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorAgendarConsulta {

    private final PacienteRepository repository;

    @Autowired
    public PacienteActivo(PacienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(AgendarConsultaDTO datos) {
        if (datos.idPaciente() == null) return;

        var pacienteActivo = repository.findActivoById(datos.idPaciente());
        if (!pacienteActivo) throw new ValidationException("No se permite agendar citas con pacientes inactivos.");
    }
}
