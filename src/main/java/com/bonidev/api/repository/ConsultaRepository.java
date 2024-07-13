package com.bonidev.api.repository;

import com.bonidev.api.model.entity.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
}
