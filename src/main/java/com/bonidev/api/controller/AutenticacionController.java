package com.bonidev.api.controller;

import com.bonidev.api.dto.security.AutenticacionDTO;
import com.bonidev.api.dto.security.JWTTokenDTO;
import com.bonidev.api.model.entity.UsuarioEntity;
import com.bonidev.api.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid AutenticacionDTO datosAutenticacionUsuario) {
        Authentication token = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        authenticationManager.authenticate(token);

        var usuarioAuth = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.generarToken((UsuarioEntity) usuarioAuth.getPrincipal());

        return ResponseEntity.ok(new JWTTokenDTO(tokenJWT));
    }
}
