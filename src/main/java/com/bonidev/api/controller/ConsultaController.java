package com.bonidev.api.controller;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.dto.consulta.CancelarConsultaDTO;
import com.bonidev.api.dto.consulta.DetalleConsultaDTO;
import com.bonidev.api.service.ConsultaService;
import com.bonidev.api.validaciones.ValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Permite agendar una consulta.",
            description = "Permite agendar una consulta con los datos proporcionados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Consulta agendada con éxito.",
                            content = @Content(
                                    schema = @Schema(implementation = DetalleConsultaDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error de validación en los datos proporcionados.",
                            content = @Content(
                                    schema = @Schema(implementation = ValidationException.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<DetalleConsultaDTO> agendar(@RequestBody @Valid AgendarConsultaDTO datos) {
        return ResponseEntity.ok(consultaService.agendar(datos));
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
