package com.alurachallenge.forohub.jokerp515.foro_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.DatosRegistroUsuario;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.DatosRespuestaUsuario;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.Usuario;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario) {
        // Codificar la contraseña antes de guardarla
        String encodedPassword = passwordEncoder.encode(datosRegistroUsuario.clave());
        Usuario nuevoUsuario = new Usuario(datosRegistroUsuario.login(), datosRegistroUsuario.email(), encodedPassword);
        usuarioRepository.save(nuevoUsuario);
        return ResponseEntity.ok(new DatosRespuestaUsuario(nuevoUsuario.getId(), nuevoUsuario.getLogin(), nuevoUsuario.getEmail()));
    }
}
