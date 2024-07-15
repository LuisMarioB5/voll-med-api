package com.bonidev.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class ConsultaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoEntity medicoEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteEntity pacienteEntity;

    @Column(name = "fecha_consulta")
    private LocalDateTime fechaConsulta;

    @Setter
    @Column(name = "esta_activa")
    private Boolean estaActiva;

    @PrePersist
    protected void onCreate() {
        if (fechaConsulta == null) fechaConsulta = LocalDateTime.now();
        if (estaActiva == null) estaActiva = true;
    }

    public void activar() {
        this.estaActiva = true;
    }

    public void desactivar() {
        this.estaActiva = false;
    }
}
