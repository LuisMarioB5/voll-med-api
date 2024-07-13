package com.bonidev.api.model.entity;

import com.bonidev.api.dto.medico.ActualizarMedicoDTO;
import com.bonidev.api.dto.medico.RegistrarMedicoDTO;
import com.bonidev.api.model.Especialidad;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.*;

@Transactional
@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String documento;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Column(unique = true)
    private String telefono;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "direccion_id")
    @JsonManagedReference
    @Valid
    private DireccionEntity direccionEntity;

    @Setter
    private Boolean estaActivo = true;

    public MedicoEntity(RegistrarMedicoDTO dto) {
        this.nombre = dto.nombre();
        this.email = dto.email();
        this.documento = dto.documento();
        this.especialidad = dto.especialidad();
        this.telefono = dto.telefono();
        this.direccionEntity = new DireccionEntity(dto.direccion());
    }

    @Override
    public String toString() {
        return String.format("""
                 ######################## MÉDICO ########################
                 Nombre: %s
                 Email: %s
                 Documento: %s
                 Especialidad: %s
                 Telefono: %s
                 ########################################################
                """, nombre, email, documento, especialidad, telefono);
    }

    public void actualizarDatos(ActualizarMedicoDTO medico) {
        if (medico.nombre() != null) this.nombre = medico.nombre();
        if (medico.documento() != null) this.documento = medico.documento();
        if (medico.direccion() != null) this.direccionEntity = direccionEntity.actualizarDatos(medico.direccion());
    }
}
