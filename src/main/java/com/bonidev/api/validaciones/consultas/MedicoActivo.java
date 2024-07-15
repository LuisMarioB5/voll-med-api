package com.bonidev.api.validaciones.consultas;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.repository.MedicoRepository;
import com.bonidev.api.validaciones.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorAgendarConsulta {

    private final MedicoRepository repository;

    @Autowired
    public MedicoActivo(MedicoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(AgendarConsultaDTO datos) {
        if (datos.idMedico() == null) return;

        var medicoActivo = repository.findActivoById(datos.idMedico());
        if (!medicoActivo) throw new ValidationException("No se permite agendar citas con médicos inactivos.");
    }
}
