package com.bonidev.api.repository;

import com.bonidev.api.model.entity.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    boolean existsByPacienteEntityIdAndFechaConsultaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoEntityIdAndFechaConsulta(Long idMedico, LocalDateTime fecha);
}
