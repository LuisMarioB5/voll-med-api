package com.bonidev.api.validaciones;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository repository;

    public void validar(AgendarConsultaDTO datos) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);

        var pacienteConConsulta = repository.existsByPacienteEntityIdAndFechaConsultaBetween(datos.idPaciente(), primerHorario, ultimoHorario);
        if (pacienteConConsulta) throw new ValidationException("El mismo paciente no puede tener más de una consulta al día.");

    }
}
