package com.bonidev.api.controller;

import com.bonidev.api.dto.paciente.ActualizarPacienteDTO;
import com.bonidev.api.dto.paciente.DetallesPacienteDTO;
import com.bonidev.api.dto.paciente.ListaPacienteDTO;
import com.bonidev.api.dto.paciente.RegistrarPacienteDTO;
import com.bonidev.api.model.entity.MedicoEntity;
import com.bonidev.api.model.entity.PacienteEntity;
import com.bonidev.api.response.MultiStatusResponse;
import com.bonidev.api.service.PacienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired
    private PacienteService service;

    @PostMapping
    public ResponseEntity<?> registrarPaciente(@RequestBody @Valid RegistrarPacienteDTO dto) {
        if (dto == null) {
            return ResponseEntity.badRequest().body("No puede registrar un paciente si no se tienen datos del mismo.");
        }

        PacienteEntity paciente = service.guardar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(paciente.getId())
                .toUri();

        return ResponseEntity.created(location).body("El paciente fue almacenado satisfactoriamente.");
    }

    @GetMapping
    public ResponseEntity<Page<ListaPacienteDTO>> listarPacientesActivos(@PageableDefault(size = 10, sort = "{nombre}") Pageable pageable) {
        return ResponseEntity.ok(service.encontrarPacientesActivos(pageable));
    }

    @PutMapping //NO FUNCIONA
    public ResponseEntity<DetallesPacienteDTO> actualizarPaciente(@RequestBody @Valid ActualizarPacienteDTO dto) {
        return ResponseEntity.ok(service.actualizar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarPaciente(@PathVariable Long id) {
        service.desactivar(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> activarPaciente(@PathVariable Long id) {
        service.activar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detallesPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(service.detallesPaciente(id));
    }

}
