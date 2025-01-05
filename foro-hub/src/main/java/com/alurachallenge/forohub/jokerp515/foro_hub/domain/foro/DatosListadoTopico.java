package com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro;

import java.time.LocalDateTime;

public record DatosListadoTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, Estado estado,
        Long idAutor, String autorName, String curso) {
    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getEstado(),
                topico.getIdAutor(), topico.getAutorName(), topico.getCurso());
    }
}
