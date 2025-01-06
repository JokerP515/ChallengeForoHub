package com.alurachallenge.forohub.jokerp515.foro_hub.infra.errores;

public class JsonVacioException extends RuntimeException {
    public JsonVacioException(String mensaje) {
        super(mensaje);
    }
}