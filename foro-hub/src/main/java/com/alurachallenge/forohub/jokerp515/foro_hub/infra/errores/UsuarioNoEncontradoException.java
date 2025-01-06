package com.alurachallenge.forohub.jokerp515.foro_hub.infra.errores;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}