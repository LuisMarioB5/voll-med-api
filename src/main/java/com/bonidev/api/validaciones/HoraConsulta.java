package com.bonidev.api.validaciones;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class HoraConsulta implements ValidadorDeConsultas {
    public void validar(AgendarConsultaDTO datos){
        var horaAhora = LocalDateTime.now();
        var horaDeLaConsulta = datos.fecha();

        var diferenciaEntreFechas = ChronoUnit.MINUTES.between(horaAhora, horaDeLaConsulta);

        if (diferenciaEntreFechas < 30) {
            throw new ValidationException("Las consultas deben ser programadas con al menos 30 minutos de anticipación. Diferencia actual: " + diferenciaEntreFechas + " minutos.");
        }
    }
}
