package com.bonidev.api.validaciones;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository repository;

    public void validar(AgendarConsultaDTO datos) {
        if (datos.idMedico() == null) return;

        var medicoConConsulta = repository.existsByMedicoEntityIdAndFechaConsulta(datos.idMedico(), datos.fecha());
        if (medicoConConsulta) throw new ValidationException("Este médico tiene una consulta en el mismo horario.");
    }
}
