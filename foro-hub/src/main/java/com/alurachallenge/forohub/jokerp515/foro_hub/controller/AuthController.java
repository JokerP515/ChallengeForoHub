package com.alurachallenge.forohub.jokerp515.foro_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.DatosAuthUsuario;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.Usuario;
import com.alurachallenge.forohub.jokerp515.foro_hub.infra.security.DatosJWToken;
import com.alurachallenge.forohub.jokerp515.foro_hub.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosJWToken> autenticarUsuario(
            @RequestBody @Valid DatosAuthUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        Authentication userAuth = authenticationManager.authenticate(authToken);

        String JWToken = tokenService.generarToken((Usuario) userAuth.getPrincipal());

        return ResponseEntity.ok(new DatosJWToken(JWToken));
    }
}
