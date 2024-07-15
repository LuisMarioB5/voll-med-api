package com.bonidev.api.controller;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.dto.consulta.CancelarConsultaDTO;
import com.bonidev.api.dto.consulta.DetalleConsultaDTO;
import com.bonidev.api.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<DetalleConsultaDTO> agendar(@RequestBody @Valid AgendarConsultaDTO datos) {
        return  ResponseEntity.ok(new DetalleConsultaDTO(consultaService.agendar(datos)));
    }

    @DeleteMapping
    public ResponseEntity<?> cancelar(@RequestBody @Valid CancelarConsultaDTO datos) {
        consultaService.cancelar(datos);
        return ResponseEntity.noContent().build();
    }
}
