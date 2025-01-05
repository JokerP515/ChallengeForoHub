package com.alurachallenge.forohub.jokerp515.foro_hub.domain.foro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
    @NotBlank
    String titulo,
    @NotBlank
    String mensaje,
    @NotNull
    Long idAutor,
    @NotBlank
    String curso
) {
}