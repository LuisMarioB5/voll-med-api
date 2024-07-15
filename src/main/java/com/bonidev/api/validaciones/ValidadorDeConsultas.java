package com.bonidev.api.validaciones;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;

public interface ValidadorDeConsultas {
    void validar(AgendarConsultaDTO datos);
}
