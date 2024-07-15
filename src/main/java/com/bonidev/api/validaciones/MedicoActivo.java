package com.bonidev.api.validaciones;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas {
    @Autowired
    private MedicoRepository repository;

    public void validar(AgendarConsultaDTO datos) {
        if (datos.idMedico() == null) return;

        var medicoActivo = repository.findActivoById(datos.idMedico());
        if (!medicoActivo) throw new ValidationException("No se permite agendar citas con médicos inactivos.");
    }
}
