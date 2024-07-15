package com.bonidev.api.validaciones.consultas;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.repository.ConsultaRepository;
import com.bonidev.api.validaciones.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorAgendarConsulta {

    private final ConsultaRepository repository;

    @Autowired
    public MedicoConConsulta(ConsultaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(AgendarConsultaDTO datos) {
        if (datos.idMedico() == null) return;

        var medicoConConsulta = repository.existsByMedicoEntityIdAndFechaConsulta(datos.idMedico(), datos.fecha());
        if (medicoConConsulta) throw new ValidationException("Este médico tiene una consulta en el mismo horario.");
    }
}
