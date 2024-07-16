package com.bonidev.api.controller;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.dto.consulta.DetalleConsultaDTO;
import com.bonidev.api.model.Especialidad;
import com.bonidev.api.model.entity.ConsultaEntity;
import com.bonidev.api.service.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    private final MockMvc mvc;
    private final JacksonTester<AgendarConsultaDTO> agendarConsultaDTOJacksonTester;
    private final JacksonTester<DetalleConsultaDTO> detalleConsultaDTOJacksonTester;
    @MockBean
    private final ConsultaService consultaService;

    @Autowired
    public ConsultaControllerTest(MockMvc mvc, JacksonTester<AgendarConsultaDTO> agendarConsultaDTOJacksonTester, JacksonTester<DetalleConsultaDTO> detalleConsultaDTOJacksonTester, ConsultaService consultaService) {
        this.mvc = mvc;
        this.agendarConsultaDTOJacksonTester = agendarConsultaDTOJacksonTester;
        this.detalleConsultaDTOJacksonTester = detalleConsultaDTOJacksonTester;
        this.consultaService = consultaService;
    }

    @Test
    @DisplayName("Debería retornar estado HTTP 400 cuando los datos sean invalidos")
    @WithMockUser
    void agendarEscenario1() throws Exception {
        // given // when
        var response = mvc.perform(post("/consulta")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería retornar estado HTTP 200 cuando los datos ingresados son validos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        // given
        var fecha = LocalDateTime.now().plusHours(1);
        var detalleConsulta = new DetalleConsultaDTO(null, 2L, 5L, Especialidad.CARDIOLOGIA.capitalize(), fecha);

        // when
        when(consultaService.agendar(any())).thenReturn(detalleConsulta);

        var response = mvc.perform(post("/consulta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(agendarConsultaDTOJacksonTester.write(new AgendarConsultaDTO(null, 2L, 5L, Especialidad.CARDIOLOGIA, fecha, true)).getJson())
        ).andReturn().getResponse();

        var jsonEsperado = detalleConsultaDTOJacksonTester.write(detalleConsulta).getJson();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Debería retornar estado HTTP 404 cuando no se encuentra el recurso en el servidor")
    @WithMockUser
    void agendarEscenario3() throws Exception {
        // given
        var fecha = LocalDateTime.now().plusHours(1);

        // when
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}