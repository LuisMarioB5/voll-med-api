package com.bonidev.api.model.entity;

import com.bonidev.api.dto.direccion.DireccionDTO;
import com.bonidev.api.dto.paciente.ActualizarPacienteDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "direcciones")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DireccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;

    private String distrito;

    private String ciudad;

    private String numero;

    private String complemento;

    @OneToOne(mappedBy = "direccionEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id")
    @JsonBackReference
    private MedicoEntity medico;

    @OneToOne(mappedBy = "direccion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id")
    @JsonBackReference
    private PacienteEntity pacienteEntity;

    public DireccionEntity(DireccionDTO dto) {
        this.calle = dto.calle();
        this.distrito = dto.distrito();
        this.ciudad = dto.ciudad();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
    }

    public DireccionEntity actualizarDatos(DireccionEntity direccion) {
        this.calle = calle;
        this.distrito = distrito;
        this.ciudad = ciudad;
        this.numero = numero;
        this.complemento = complemento;
        return this;
    }
}
