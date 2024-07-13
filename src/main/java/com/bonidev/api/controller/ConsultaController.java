package com.bonidev.api.controller;

import com.bonidev.api.dto.consulta.AgendarConsultaDTO;
import com.bonidev.api.dto.consulta.DetalleConsultaDTO;
import com.bonidev.api.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody @Valid AgendarConsultaDTO datos) {
//        System.out.println(datos); // PRUEBA

        return  ResponseEntity.ok(new DetalleConsultaDTO(consultaService.agendar(datos)));
    }
}
