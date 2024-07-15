package com.bonidev.api.validaciones.consultas;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.validaciones.ValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioClinica implements ValidadorAgendarConsulta {

    @Override
    public void validar(AgendarConsultaDTO datos) {
        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        var horaDeLaConsulta = datos.fecha().getHour();

        if (domingo || (horaDeLaConsulta < 7) || (horaDeLaConsulta > 19)) {
            throw new ValidationException("El horario de atención de la clínica es de lunes a sábado de 07:00 a 19:00 horas.");
        }
    }
}
