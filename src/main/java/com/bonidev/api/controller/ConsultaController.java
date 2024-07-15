package com.bonidev.api.controller;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.dto.consulta.CancelarConsultaDTO;
import com.bonidev.api.dto.consulta.DetalleConsultaDTO;
import com.bonidev.api.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> cancelar(@RequestBody CancelarConsultaDTO datos) {
        consultaService.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/all")
    public ResponseEntity<String> activarTodas() {
        consultaService.activarTodas();
        return ResponseEntity.ok("Todas las consultas se establecieron como activas.");
    }

    @GetMapping
    public ResponseEntity<List<DetalleConsultaDTO>> mostrarConsultas() {
        return ResponseEntity.ok(consultaService.mostrarTodas());
    }
}
