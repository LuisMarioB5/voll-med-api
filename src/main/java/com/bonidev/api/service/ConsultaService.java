package com.bonidev.api.service;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.dto.consulta.CancelarConsultaDTO;
import com.bonidev.api.dto.consulta.DetalleConsultaDTO;
import com.bonidev.api.model.entity.ConsultaEntity;
import com.bonidev.api.model.entity.MedicoEntity;
import com.bonidev.api.model.entity.PacienteEntity;
import com.bonidev.api.repository.ConsultaRepository;
import com.bonidev.api.repository.MedicoRepository;
import com.bonidev.api.repository.PacienteRepository;
import com.bonidev.api.validaciones.consultas.ValidadorAgendarConsulta;
import com.bonidev.api.validaciones.ValidationException;
import com.bonidev.api.validaciones.consultas.ValidadorCancelarConsulta;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorAgendarConsulta> validadoresAgendarConsulta;
    private final List<ValidadorCancelarConsulta> validadoresCancelarConsulta;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository,
                           MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository,
                           List<ValidadorAgendarConsulta> validadoresAgendarConsulta,
                           List<ValidadorCancelarConsulta> validadoresCancelarConsulta) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadoresAgendarConsulta = validadoresAgendarConsulta;
        this.validadoresCancelarConsulta = validadoresCancelarConsulta;
    }

    public DetalleConsultaDTO agendar(AgendarConsultaDTO datos) {
        MedicoEntity medico = obtenerMedico(datos);
        PacienteEntity paciente = obtenerPaciente(datos.idPaciente());

        validarAgendarConsulta(datos);

        ConsultaEntity consulta = new ConsultaEntity(datos.id(), medico, paciente, datos.fecha(), datos.estaActiva());
        consultaRepository.save(consulta);

        return new DetalleConsultaDTO(consulta);
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

    private void validarAgendarConsulta(AgendarConsultaDTO datos) {
        validadoresAgendarConsulta.forEach(validador -> validador.validar(datos));
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
        if (datos.consultaId() == null) {
            throw new ValidationException("El id de la consulta no puede ser nulo.");
        }

        ConsultaEntity consulta = consultaRepository.getReferenceById(datos.consultaId());

        validarCancelarConsulta(datos, consulta);
        consulta.desactivar();
    }

    private void validarCancelarConsulta(CancelarConsultaDTO datos, ConsultaEntity consulta) {
        validadoresCancelarConsulta.forEach(validador -> validador.validar(datos, consulta));
    }

    public void activarTodas() {
        List<ConsultaEntity> consultas = consultaRepository.findAll();

        consultas.forEach(ConsultaEntity::activar);
    }

    public List<DetalleConsultaDTO> mostrarTodas() {
        List<ConsultaEntity> consultas = consultaRepository.findAll().stream()
                .filter(ConsultaEntity::getEstaActiva)
                .toList();

        List<DetalleConsultaDTO> consultasDTO = new java.util.ArrayList<>(List.of());
        consultas.forEach(consulta -> consultasDTO.add(new DetalleConsultaDTO(consulta)));

        return consultasDTO;
    }
}
