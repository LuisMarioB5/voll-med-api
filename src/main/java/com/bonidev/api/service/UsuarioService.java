package com.bonidev.api.service;

import com.bonidev.api.dto.security.AutenticacionDTO;
import com.bonidev.api.model.entity.UsuarioEntity;
import com.bonidev.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioEntity save(AutenticacionDTO dto) {
        UsuarioEntity usuario = new UsuarioEntity(dto.login(), dto.clave());
        return repository.save(usuario);
    }
}
