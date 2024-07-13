package com.bonidev.api.model.entity;

import com.bonidev.api.dto.paciente.ActualizarPacienteDTO;
import com.bonidev.api.dto.paciente.RegistrarPacienteDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "pacientes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class PacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String nombre;

    private String email;

    private String telefono;

    private String documento;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "direccion_id")
    @JsonManagedReference
    @Valid
    private DireccionEntity direccion;

    @Column(name = "esta_activo")
    private Boolean estaActivo;

    public PacienteEntity(RegistrarPacienteDTO dto) {
        this.nombre = dto.nombre();
        this.email = dto.email();
        this.telefono = dto.telefono();
        this.documento = dto.documento();
        this.direccion = new DireccionEntity(dto.direccion());
        this.estaActivo = true;
    }

    public void actualizarInformacion(ActualizarPacienteDTO dto) {
        if (dto.nombre() != null) {
            this.nombre = dto.nombre();
        }

        if (dto.telefono() != null) {
            this.telefono = dto. telefono();
        }

        if (dto.direccion() != null) {
            this.direccion.actualizarDatos(dto.direccion());
        }
    }

    public void desactivar() {
        this.estaActivo = false;
    }

    public void activar() {
        this.estaActivo = true;
    }
}
