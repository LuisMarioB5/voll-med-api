package com.bonidev.api.service;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.dto.consulta.CancelarConsultaDTO;
import com.bonidev.api.model.entity.ConsultaEntity;
import com.bonidev.api.model.entity.MedicoEntity;
import com.bonidev.api.model.entity.PacienteEntity;
import com.bonidev.api.repository.ConsultaRepository;
import com.bonidev.api.repository.MedicoRepository;
import com.bonidev.api.repository.PacienteRepository;
import com.bonidev.api.validaciones.ValidadorDeConsultas;
import com.bonidev.api.validaciones.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorDeConsultas> validadores;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository,
                           MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository,
                           List<ValidadorDeConsultas> validadores) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;
    }

    public ConsultaEntity agendar(AgendarConsultaDTO datos) {
        MedicoEntity medico = obtenerMedico(datos);
        PacienteEntity paciente = obtenerPaciente(datos.idPaciente());

        validarDatos(datos);

        ConsultaEntity consulta = new ConsultaEntity(datos.id(), medico, paciente, datos.fecha());
        consultaRepository.save(consulta);

        return consulta;
    }

    private MedicoEntity obtenerMedico(AgendarConsultaDTO datos) {
        if (datos.idMedico() != null) {
            return medicoRepository.findById(datos.idMedico())
                    .orElseThrow(() -> new ValidationException("Medico no encontrado"));
        } else {
            return seleccionarMedico(datos);
        }
    }

    private PacienteEntity obtenerPaciente(Long idPaciente) {
        return pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new ValidationException("Paciente no encontrado"));
    }

    private void validarDatos(AgendarConsultaDTO datos) {
        validadores.forEach(validador -> validador.validar(datos));
    }

    private MedicoEntity seleccionarMedico(AgendarConsultaDTO datos) {
        if (datos.especialidad() == null) {
            throw new ValidationException("Debe elegir una especialidad para el médico");
        }

        List<MedicoEntity> medicos = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
        if (medicos.isEmpty()) {
            throw new ValidationException("No se encontró ningún médico que encaje con la solicitud");
        }

        Collections.shuffle(medicos); // Mezcla la lista de médicos para simular aleatoriedad
        return medicos.get(0); // Selecciona el primer médico luego de mezclar la lista aleatoriamente
    }

    public void cancelar(CancelarConsultaDTO datos) {
        

    }
}
