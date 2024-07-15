package com.bonidev.api.service;

import com.bonidev.api.dto.medico.MostrarMedicoDTO;
import com.bonidev.api.dto.paciente.ActualizarPacienteDTO;
import com.bonidev.api.dto.paciente.DetallesPacienteDTO;
import com.bonidev.api.dto.paciente.ListaPacienteDTO;
import com.bonidev.api.dto.paciente.RegistrarPacienteDTO;
import com.bonidev.api.model.entity.PacienteEntity;
import com.bonidev.api.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PacienteService {

    private final PacienteRepository repository;

    @Autowired
    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public PacienteEntity guardar(RegistrarPacienteDTO paciente) {
        return repository.save(new PacienteEntity(paciente));
    }

    public Page<ListaPacienteDTO> encontrarPacientesActivos(Pageable pageable) {
        List<ListaPacienteDTO> dto = repository.findAll().stream()
                .filter(paciente -> paciente.getEstaActivo().equals(true))
                .map(ListaPacienteDTO::new)
                .toList();

        return new PageImpl<ListaPacienteDTO>(dto);
    }

    public DetallesPacienteDTO actualizar(ActualizarPacienteDTO datos) {
        PacienteEntity paciente = repository.getReferenceById(datos.id());
        paciente.actualizarInformacion(datos);

        return new DetallesPacienteDTO(paciente);
    }

    public void desactivar(Long id) {
        PacienteEntity paciente = repository.getReferenceById(id);
        paciente.desactivar();
    }

    public void activar(Long id) {
        PacienteEntity paciente = repository.getReferenceById(id);
        paciente.activar();
    }

    public DetallesPacienteDTO detallesPaciente(Long id) {
        PacienteEntity paciente = repository.getReferenceById(id);

        return new DetallesPacienteDTO(paciente);
    }
}
