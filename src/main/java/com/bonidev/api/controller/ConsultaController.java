package com.bonidev.api.controller;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.dto.consulta.CancelarConsultaDTO;
import com.bonidev.api.dto.consulta.DetalleConsultaDTO;
import com.bonidev.api.service.ConsultaService;
import com.bonidev.api.validaciones.ValidationException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    /**
     * Permite agendar una consulta.
     *
     * @param datos los datos necesarios para agendar la consulta, encapsulados en un {@link AgendarConsultaDTO}.
     * @return un {@link ResponseEntity} que contiene el {@link DetalleConsultaDTO} de la consulta agendada, con un código HTTP 200 (OK).
     * @throws ValidationException si hay algún error de validación en los datos proporcionados.
     */
    @PostMapping
    public ResponseEntity<DetalleConsultaDTO> agendar(@RequestBody @Valid AgendarConsultaDTO datos) {
        return ResponseEntity.ok(new DetalleConsultaDTO(consultaService.agendar(datos)));
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
