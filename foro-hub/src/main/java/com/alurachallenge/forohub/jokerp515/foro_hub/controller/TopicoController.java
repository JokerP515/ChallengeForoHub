package com.alurachallenge.forohub.jokerp515.foro_hub.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.DatosActualizarTopico;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.DatosListadoTopico;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.DatosRegistroTopico;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.Topico;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro.TopicoRepository;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.Usuario;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.UsuarioRepository;
import com.alurachallenge.forohub.jokerp515.foro_hub.infra.errores.UsuarioNoEncontradoException;
import com.alurachallenge.forohub.jokerp515.foro_hub.infra.errores.JsonVacioException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
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
    public ResponseEntity<DatosListadoTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
            UriComponentsBuilder uriComponentsBuilder) {
        // Primero vamos a verificar si el usuario existe
        Optional<Usuario> autor = usuarioRepository.findById(datosRegistroTopico.idAutor());
        // Ahora si podemos registrar el topico
        if (autor.isEmpty()) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, autor.get()));
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosListadoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 3) Pageable pageable) {
        return ResponseEntity.ok(topicoRepository.findAll(pageable).map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> getDatosTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        DatosListadoTopico datos = new DatosListadoTopico(topico);
        return ResponseEntity.ok(datos);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosListadoTopico> actualizarTopico(@PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico == null || (datosActualizarTopico.titulo() == null
        && datosActualizarTopico.mensaje() == null && datosActualizarTopico.estado() == null
        && datosActualizarTopico.curso() == null)) {
            throw new JsonVacioException("El cuerpo de la solicitud está vacío");
        }
        Topico topico = topicoRepository.getReferenceById(id);
        topico.actualizarTopico(datosActualizarTopico);
        return ResponseEntity.ok(new DatosListadoTopico(topico));
    }

    // Delete de la base de datos
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }
}