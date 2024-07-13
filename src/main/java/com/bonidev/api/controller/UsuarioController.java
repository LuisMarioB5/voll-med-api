package com.bonidev.api.controller;

import com.bonidev.api.dto.security.AutenticacionDTO;
import com.bonidev.api.model.entity.UsuarioEntity;
import com.bonidev.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid AutenticacionDTO user) {
        UsuarioEntity userEntity = usuarioService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userEntity.getId())
                .toUri();
        return ResponseEntity.created(location).body("El usuario fue almacenado satisfactoriamente.");
    }
}
