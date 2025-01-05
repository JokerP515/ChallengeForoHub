package com.alurachallenge.forohub.jokerp515.foro_hub.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.DatosListadoTopico;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.DatosRegistroTopico;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.Topico;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.TopicoRepository;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.Usuario;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.UsuarioRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosRegistroTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
            UriComponentsBuilder uriComponentsBuilder) {
        // Primero vamos a verificar si el usuario existe
        Usuario autor = usuarioRepository.findById(datosRegistroTopico.idAutor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        // Ahora si podemos registrar el topico
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, autor));
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRegistroTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 3) Pageable pageable) {
        return ResponseEntity.ok(topicoRepository.findAll(pageable).map(DatosListadoTopico::new));
    }
}