package com.bonidev.api.validaciones.consultas;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.dto.consulta.CancelarConsultaDTO;
import com.bonidev.api.model.entity.ConsultaEntity;
import com.bonidev.api.validaciones.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class HoraCancelarConsulta implements ValidadorCancelarConsulta {

    @Override
    public void validar(CancelarConsultaDTO datos, ConsultaEntity consulta) {
        if (datos.motivoCancelacion() == null) {
            throw new ValidationException("No puede cancelar una consulta sin proporcionar el motivo de cancelación.");
        }

        var fechaAhora = LocalDateTime.now();
        var fechaConsulta = consulta.getFechaConsulta();

        var diferenciaHoras = ChronoUnit.HOURS.between(fechaAhora, fechaConsulta);

        if (diferenciaHoras < 24) {
            throw new ValidationException("No se permite cancelar consultas con menos de un día (24 horas) de anticipación a la consulta.");
        }
    }
}
