package com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(
    @NotBlank
    String login,
    @NotBlank
    String email,
    @NotBlank
    String clave
) {
}