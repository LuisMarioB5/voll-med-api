package com.bonidev.api.controller;

import com.bonidev.api.dto.paciente.ActualizarPacienteDTO;
import com.bonidev.api.dto.paciente.DetallesPacienteDTO;
import com.bonidev.api.dto.paciente.ListaPacienteDTO;
import com.bonidev.api.dto.paciente.RegistrarPacienteDTO;
import com.bonidev.api.model.entity.PacienteEntity;
import com.bonidev.api.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService service;

    @PostMapping
    public ResponseEntity<DetallesPacienteDTO> registrarPaciente(@RequestBody @Valid RegistrarPacienteDTO dto, UriComponentsBuilder builder) {
        PacienteEntity paciente = service.guardar(dto);

        URI uri = builder.path("/{id}")
                .buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetallesPacienteDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<ListaPacienteDTO>> listarPacientesActivos(@PageableDefault(size = 10, sort = "{nombre}") Pageable pageable) {
        return ResponseEntity.ok(service.encontrarPacientesActivos(pageable));
    }

    @PutMapping
    public ResponseEntity<DetallesPacienteDTO> actualizarPaciente(@RequestBody @Valid ActualizarPacienteDTO dto) {
        return ResponseEntity.ok(service.actualizar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarPaciente(@PathVariable Long id) {
        service.desactivar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detallesPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(service.detallesPaciente(id));
    }

}
