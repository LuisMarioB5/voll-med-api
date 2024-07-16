package com.bonidev.api.controller;

import com.bonidev.api.dto.medico.ActualizarMedicoDTO;
import com.bonidev.api.dto.medico.MostrarMedicoDTO;
import com.bonidev.api.dto.medico.RegistrarMedicoDTO;
import com.bonidev.api.model.entity.MedicoEntity;
import com.bonidev.api.response.MultiStatusResponse;
import com.bonidev.api.service.MedicoService;
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

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public ResponseEntity<?> registrarMedico(@RequestBody @Valid List<RegistrarMedicoDTO> medicosDTO) {
        if (medicosDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista de médicos no puede estar vacía.");
        }

        if (medicosDTO.size() == 1) {
            MedicoEntity medico = medicoService.save(medicosDTO.get(0));
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(medico.getId())
                    .toUri();

            return ResponseEntity.created(location).body("El médico fue almacenado satisfactoriamente.");
        } else {
            MultiStatusResponse response = medicoService.saveAll(medicosDTO);

            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> mostrarMedicos(@PageableDefault(size = 5) Pageable paginacion) {
        Page<MostrarMedicoDTO> medicos = medicoService.mostrarTodosLosMedicos(paginacion);

        if (medicos.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(medicos);
    }

    @PutMapping //NO FUNCIONA
    public ResponseEntity<String> actualizarDatosMedico(@RequestBody @Valid ActualizarMedicoDTO dto) {
        medicoService.actualizarMedico(dto);
        return ResponseEntity.ok("Los datos se actualizaron correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> desactivarMedico(@PathVariable Long id) {
        medicoService.desactivarMedico(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/activarMedicos")
    public ResponseEntity<String> activarMedico() {
        medicoService.activarMedicos();
        return ResponseEntity.ok("Todos los médicos de la base de datos fueron activados satisfactoriamente.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostrarMedico(@PathVariable Long id) {
        MostrarMedicoDTO medicoDTO = medicoService.mostrarMedico(id);

        if (medicoDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("El médico con el id %s no se encuentra o está desactivado.", id));
        }

        return ResponseEntity.ok(medicoDTO);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> desactivarMedicos() {
        medicoService.desactivarMedicos();
        return ResponseEntity.noContent().build();

    }
}
