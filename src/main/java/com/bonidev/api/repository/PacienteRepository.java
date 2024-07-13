package com.bonidev.api.repository;

import com.bonidev.api.model.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity, Long> {
    @Query("SELECT p.estaActivo FROM PacienteEntity p WHERE p.id = :idPaciente")
    Boolean findActivoById(Long idPaciente);

}