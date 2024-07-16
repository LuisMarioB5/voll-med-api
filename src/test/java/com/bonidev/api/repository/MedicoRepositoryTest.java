package com.bonidev.api.repository;

import com.bonidev.api.dto.direccion.DireccionDTO;
import com.bonidev.api.dto.medico.RegistrarMedicoDTO;
import com.bonidev.api.dto.paciente.RegistrarPacienteDTO;
import com.bonidev.api.model.Especialidad;
import com.bonidev.api.model.entity.ConsultaEntity;
import com.bonidev.api.model.entity.DireccionEntity;
import com.bonidev.api.model.entity.MedicoEntity;
import com.bonidev.api.model.entity.PacienteEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.assertj.core.internal.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    private final MedicoRepository repository;
    private final TestEntityManager em;

    @Autowired
    public MedicoRepositoryTest(MedicoRepository repository, TestEntityManager em) {
        this.repository = repository;
        this.em = em;
    }

    @Test
    @DisplayName("Debería retornar una lista vacía cuando el médico se encuentre en cita con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("Ricardo Estevez", "ricardo.estevez@voll.med", "456321", Especialidad.CARDIOLOGIA, "111-222-3333", "calleX", "distritoX", "ciudadX", "X", "Y");
        var paciente = registrarPaciente("Eufemio Sánchez", "eufemio.sanchez@paciente.voll", "000-222-1133", "894566", "calleY", "distritoY", "ciudadY", "Y", "X");

        registrarConsulta(medico, paciente, proximoLunes10H);

        var medicoLibre = repository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);

        assertThat(medicoLibre).isEqualTo(List.of());
    }

    @Test
    @DisplayName("Debería retornar una lista con un solo médico cuando realice la consulta en la base de datos para ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("Ricardo Estevez", "ricardo.estevez@voll.med", "456321", Especialidad.CARDIOLOGIA, "111-222-3333", "calleX", "distritoX", "ciudadX", "X", "Y");

        var medicoLibre = repository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);

        assertThat(medicoLibre).isEqualTo(List.of(medico));
    }

    private void registrarConsulta(MedicoEntity medico, PacienteEntity paciente, LocalDateTime fecha) {
        em.persist(new ConsultaEntity(medico, paciente, fecha, true));
    }

    private MedicoEntity registrarMedico(String nombre, String email, String documento, Especialidad especialidad, String telefono, String calle, String distrito, String ciudad, String numero, String complemento) {
        var medico = new MedicoEntity(toRegistrarMedicoDTO(nombre, email, documento, especialidad, telefono, toDireccionDTO(calle, distrito, ciudad, numero, complemento)));
        em.persist(medico);
        return medico;
    }

    private PacienteEntity registrarPaciente(String nombre, String email, String telefono, String documento, String calle, String distrito, String ciudad, String numero, String complemento) {
        var paciente = new PacienteEntity(toRegistrarPacienteDTO(nombre, email, telefono, documento, toDireccionDTO(calle, distrito, ciudad, numero, complemento)));
        em.persist(paciente);
        return paciente;
    }

    private RegistrarMedicoDTO toRegistrarMedicoDTO(String nombre, String email, String documento, Especialidad especialidad, String telefono, DireccionDTO direccion) {
        return new RegistrarMedicoDTO(nombre, email, documento, especialidad, telefono, direccion);
    }

    private RegistrarPacienteDTO toRegistrarPacienteDTO(String nombre, String email, String telefono, String documento, DireccionDTO direccion) {
        return new RegistrarPacienteDTO(nombre, email, telefono, documento, direccion);
    }

    private DireccionDTO toDireccionDTO(String calle, String distrito, String ciudad, String numero, String complemento) {
        return new DireccionDTO(calle, distrito, ciudad, numero, complemento);
    }
}