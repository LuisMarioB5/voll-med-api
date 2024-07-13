package com.bonidev.api.service;

import com.bonidev.api.dto.medico.ActualizarMedicoDTO;
import com.bonidev.api.dto.medico.MostrarMedicoDTO;
import com.bonidev.api.dto.medico.RegistrarMedicoDTO;
import com.bonidev.api.model.entity.MedicoEntity;
import com.bonidev.api.repository.MedicoRepository;
import com.bonidev.api.response.MultiStatusResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    public MedicoEntity save(RegistrarMedicoDTO medicoDTO) {
        MedicoEntity medico = new MedicoEntity(medicoDTO);

        return medicoRepository.save(medico);
    }

    public MultiStatusResponse saveAll(@Valid List<RegistrarMedicoDTO> medicosDTO) {
        MultiStatusResponse response = new MultiStatusResponse();

        for (RegistrarMedicoDTO dto : medicosDTO) {
            MedicoEntity medico = new MedicoEntity(dto);
            MedicoEntity savedMedico = medicoRepository.save(medico);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedMedico.getId())
                    .toUri();

            response.addResource(location.toString(), HttpStatus.CREATED.value());
        }

        return response;
    }

    public Page<MostrarMedicoDTO> mostrarTodosLosMedicos(Pageable paginacion) {
        List<MostrarMedicoDTO> medicos = medicoRepository.findAll().stream()
                                                                .filter(medico -> medico.getEstaActivo().equals(true))
                                                                .map(MostrarMedicoDTO::new)
                                                                .toList();

        return new PageImpl<MostrarMedicoDTO>(medicos);
    }

    public void actualizarMedico(ActualizarMedicoDTO medico){
        MedicoEntity medicoEntity = medicoRepository.getReferenceById(medico.id());
        medicoEntity.actualizarDatos(medico);
    }

    public void desactivarMedico(Long id) {
        MedicoEntity medicoEntity = medicoRepository.getReferenceById(id);
        medicoEntity.setEstaActivo(false);
    }

    public void desactivarMedicos() {
        List<MedicoEntity> medicoEntities = medicoRepository.findAll();

        medicoEntities.forEach(medico -> medico.setEstaActivo(false));
    }

    public void activarMedicos(){
        List<MedicoEntity> medicoEntity = medicoRepository.findAll();
        for (MedicoEntity entity : medicoEntity) {
            entity.setEstaActivo(true);
        }
    }

    public MostrarMedicoDTO mostrarMedico(Long id) {
        Optional<MedicoEntity> medico = medicoRepository.findById(id)
                                                        .filter(MedicoEntity::getEstaActivo);

        return medico.map(MostrarMedicoDTO::new).orElse(null);
    }
}
