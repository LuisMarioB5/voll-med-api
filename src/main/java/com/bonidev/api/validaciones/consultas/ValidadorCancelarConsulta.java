package com.bonidev.api.validaciones.consultas;

import com.bonidev.api.dto.consulta.CancelarConsultaDTO;
import com.bonidev.api.model.entity.ConsultaEntity;

public interface ValidadorCancelarConsulta {
    void validar(CancelarConsultaDTO datos, ConsultaEntity consulta);
}
