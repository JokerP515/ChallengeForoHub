package com.alurachallenge.forohub.jokerp515.foro_hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.Topico;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.Usuario;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.UsuarioRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

// Pendiente de hacer ajustes al devolver topicos al usuario // parte no fundamental del programa
@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping // Esto lista los topicos del usuario autenticado
    public ResponseEntity<List<Topico>> listarTopicosDelUsuario() {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        // Encontrar el usuario por su email
        UserDetails usuarioDetails = usuarioRepository.findByEmail(email);

        if(usuarioDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Usuario usuario = (Usuario) usuarioDetails;
        
        return ResponseEntity.ok(usuario.getTopicos());
    }
    
}
