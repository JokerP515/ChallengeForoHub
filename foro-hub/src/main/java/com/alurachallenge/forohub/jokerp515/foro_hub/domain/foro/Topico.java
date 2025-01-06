package com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro;

import java.time.LocalDateTime;

import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne
    private Usuario autor;
    
    private String curso;

    public Topico(DatosRegistroTopico datosRegistroTopico, Usuario autor) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.autor = autor;
        this.curso = datosRegistroTopico.curso();
        this.fechaCreacion = LocalDateTime.now();
        this.estado = Estado.ABIERTO;
    }

    public Long getIdAutor(){
        return autor.getId();
    }

    public String getAutorName(){
        return autor.getLogin();
    }

    public void actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        this.titulo = (datosActualizarTopico.titulo() == null) ? this.titulo : datosActualizarTopico.titulo();
        this.mensaje = (datosActualizarTopico.mensaje() == null) ? this.mensaje : datosActualizarTopico.mensaje();
        this.estado = (datosActualizarTopico.estado() == null) ? this.estado : datosActualizarTopico.estado();
        this.curso = (datosActualizarTopico.curso() == null) ? this.curso : datosActualizarTopico.curso();
    }
}
