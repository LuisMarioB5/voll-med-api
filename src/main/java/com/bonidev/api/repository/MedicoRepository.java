package com.bonidev.api.repository;

import com.bonidev.api.model.Especialidad;
import com.bonidev.api.model.entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
    @Query("""
        SELECT m FROM MedicoEntity m
        WHERE m.estaActivo = true
            AND m.especialidad = :especialidad
            AND m.id NOT IN (
                SELECT c.medicoEntity.id FROM ConsultaEntity c
                WHERE c.fechaConsulta = :fecha
            )
       """)
    List<MedicoEntity> seleccionarMedicoConEspecialidadEnFecha(@Param("especialidad") Especialidad especialidad, @Param("fecha") LocalDateTime fecha);
}
