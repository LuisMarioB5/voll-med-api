package com.bonidev.api.service;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.model.entity.ConsultaEntity;
import com.bonidev.api.model.entity.MedicoEntity;
import com.bonidev.api.model.entity.PacienteEntity;
import com.bonidev.api.repository.ConsultaRepository;
import com.bonidev.api.repository.MedicoRepository;
import com.bonidev.api.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public ConsultaEntity agendar(AgendarConsultaDTO datos) {
        PacienteEntity paciente = pacienteRepository.findById(datos.idPaciente()).orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        MedicoEntity medico;
        if (datos.idMedico() != null && medicoRepository.existsById(datos.idMedico())) {
            medico = medicoRepository.findById(datos.idMedico()).orElseThrow(() -> new EntityNotFoundException("Medico no encontrado"));
        } else {
            medico = seleccionarMedico(datos);
        }
        ConsultaEntity consulta = new ConsultaEntity(datos.id(), medico, paciente, datos.fecha());

        consultaRepository.save(consulta);

        return consulta;
    }

    private MedicoEntity seleccionarMedico(AgendarConsultaDTO datos) {
        if (datos.idMedico() != null) return medicoRepository.getReferenceById(datos.idMedico());

        if (datos.especialidad() == null) throw new RuntimeException("Debe elegir una especialidad para el médico");

        List<MedicoEntity> medicos = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
        if (medicos.isEmpty()) throw new RuntimeException("No se encontró ningún médico que encaje con la solicitud");
        Collections.shuffle(medicos); // Mezcla la lista de médicos para simular aleatoriedad

        return medicos.get(0); // Selecciona el primer médico luego de mezclar la lista aleatoriamente

    }
}
